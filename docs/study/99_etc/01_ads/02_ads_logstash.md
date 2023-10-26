# 도로명주소 Logstash 연동

## 도로명주소 Logstash Pipeline 설계

- 일변동 Batch 프로그램으로 수집되는 도로명주소 정보를 **Logstash** 를 활용한 **ES** 적재 자동화
- **Filebeat** 을 활용하여 특정 디렉토리 파일 생성/수정되는 경우 파일 읽어올 수 있는 `Pipeline` 설계

### Logstash 수집 Configuration

#### acs-address-v1.conf

```ruby
input {
	beats { port => 5045 }
}

filter {
 
    date {
		match => ["timestamp", "ISO8601"]
		target => "formatted_timestamp"
		timezone => "Asia/Seoul" # 원하는 시간대 설정 (예: Asia/Seoul)
    }

    mutate {
		add_field => {"log_message" => "%{message}"}
    }

    ruby {
		code => '
			event.set("thread_id", Thread.current.object_id.to_s)

			require "time"
			current_time = Time.now 
			formatted_time = current_time.strftime("%Y-%m-%d %H:%M:%S.%L")
			event.set("formatted_timestamp", formatted_time)

			require "socket"
			hostname = Socket.gethostname
			event.set("server_hostname", hostname)
		'
    }    

    if [log_type] in ["addr_doc_type_100001_MST", "addr_doc_type_100001_LNBR", "addr_doc_type_200001" ] {
	
        if [log_type] == "addr_doc_type_100001_MST" {
			
			grok {
				match => { 
					"message" => "%{WORD:roadManagementNumber}\|%{WORD:administrativeCode}\|(?<provinceName>[^|]*)\|(?<districtName>[^|]*)\|(?<neighborhoodName>[^|]*)\|(?<riName>[^|]*)\|%{WORD:mountainStatus}\|%{WORD:lotNumber}\|%{WORD:unitNumber}\|%{WORD:roadCode}\|(?<roadName>[^|]*)\|%{WORD:basementStatus}\|%{WORD:buildingNumber}\|%{WORD:buildingSubNumber}\|(?<administrativeAreaCode>[^|]*)\|(?<administrativeAreaName>[^|]*)\|%{WORD:basicAreaNumber}\|(?<previousRoadAddress>[^|]*)\|%{WORD:effectiveDate}\|%{WORD:housingType}\|(?<moveReasonCode>[^|]*)\|(?<buildingLedgerName>[^|]*)\|(?<districtLedgerName>[^|]*)\|(?<remark>[^|]*)"
				}
			}

			mutate {
				add_field => {
					"log_message_100001_MST" => "%{message}"
					"full_addr_id" => "%{roadManagementNumber}%{roadCode}%{basementStatus}%{buildingNumber}%{buildingSubNumber}"
					"roadAddrZipcode" => "%{[basicAreaNumber]}"
					"roadAddrMaster" => "%{[provinceName]}"
					"roadAddrSub" => "%{[buildingNumber]}"
					"lotNumberAddrMaster" => "%{[provinceName]} %{[neighborhoodName]}"
					"lotNumberAddrSub" => "%{[lotNumber]}"
					"administrativeAreaAddrMaster" => "%{[provinceName]} %{[neighborhoodName]}"
					"administrativeAreaAddrSub" => "%{[lotNumber]}"
					"dongName" => ""
					"noteItem" => ""
				}
			}

			if [administrativeAreaName] {
				if [districtName] {
					mutate {
						replace => ["administrativeAreaAddrMaster", "%{[provinceName]} %{[districtName]} %{[administrativeAreaName]}"]
					}
				} else {
					mutate {
						replace => ["administrativeAreaAddrMaster", "%{[provinceName]} %{[administrativeAreaName]}"]
					}
				}
			}

			if [districtName] {
				mutate {
					replace => ["roadAddrMaster", "%{roadAddrMaster} %{[districtName]}"]
					replace => ["lotNumberAddrMaster", "%{[provinceName]} %{[districtName]} %{[neighborhoodName]}"]
					replace => ["administrativeAreaAddrMaster", "%{[provinceName]} %{[districtName]} %{[neighborhoodName]}"]
				}
			}

			if [unitNumber] != '0' {
				mutate {
					replace => ["lotNumberAddrSub", "%{lotNumberAddrSub}-%{[unitNumber]}"]
					replace => ["administrativeAreaAddrSub", "%{administrativeAreaAddrSub}-%{[unitNumber]}"]
				}
			}

			if [basementStatus] == '1' {
				mutate {
					replace => ["roadAddrSub", "지하%{roadAddrSub}"]
				}
			}

			if [riName] {
				mutate {
					replace => ["lotNumberAddrMaster", "%{lotNumberAddrMaster} %{[riName]}"]
					replace => ["administrativeAreaAddrMaster", "%{administrativeAreaAddrMaster} %{[riName]}"]
					replace => ["roadAddrMaster", "%{roadAddrMaster} %{[neighborhoodName]}"]
				}   
			} else {
				mutate {
					replace => ["dongName", "%{[neighborhoodName]}"]
				}
			}

			mutate {
				replace => ["roadAddrMaster", "%{roadAddrMaster} %{[roadName]}"]
			}

			if [buildingSubNumber] != '0' {
				mutate {
					replace => ["roadAddrSub", "%{roadAddrSub}-%{[buildingSubNumber]}"]
				}
			}

			if [dongName] {
				if [housingType] == '0' {
					mutate {
						replace => ["noteItem", "(%{dongName})"]
					}
				} else {
					if [districtLedgerName] {
						mutate {
							replace => ["noteItem", "(%{dongName}, %{[districtLedgerName]})"]
						}
					} else {
						mutate {
							replace => ["noteItem", "(%{dongName})"]
						}
					}
				}
			} else {
				if [housingType] != '0' {
					if [districtLedgerName] {
						mutate {
							replace => ["noteItem", "(%{[districtLedgerName]})"]
						}
					}
				}
			}

			if [noteItem] {
				mutate {
					replace => ["roadAddrSub", "%{roadAddrSub} %{noteItem}"]
				} 
			}

			mutate {
				add_field => {
					"roadAddr" => "%{roadAddrMaster} %{roadAddrSub}"
					"lotNumberAddr" => "%{lotNumberAddrMaster} %{lotNumberAddrSub}"
				}	      
				remove_field => ["message", "dongName", "noteItem", "log", "host", "tags", "agent"]
			}
			
		}

		if [log_type] == "addr_doc_type_100001_LNBR" {
		
			grok {
				match => {
					"message" => "%{WORD:roadManagementNumber}\|%{WORD:administrativeCode}\|(?<provinceName>[^|]*)\|(?<districtName>[^|]*)\|(?<neighborhoodName>[^|]*)\|(?<riName>[^|]*)\|%{WORD:mountainStatus}\|%{WORD:lotNumber}\|%{WORD:unitNumber}\|%{WORD:roadCode}\|%{WORD:basementStatus}\|%{WORD:buildingNumber}\|%{WORD:buildingSubNumber}\|(?<moveReasonCode>[^|]*)"
				}
			}

			mutate {
				add_field => {
					"log_message_100001_LNBR" => "%{message}"
					"full_addr_id" => "%{roadManagementNumber}%{roadCode}%{basementStatus}%{buildingNumber}%{buildingSubNumber}"
					"administrativeAreaAddrSub" => "%{[lotNumber]}"
				}
				remove_field => ["message", "log", "host", "tags", "agent"]
			}

			if [unitNumber] != '0' {
				mutate {
					replace => ["administrativeAreaAddrSub", "%{administrativeAreaAddrSub}-%{[unitNumber]}"]
				}
			}

		}

		if [log_type] == "addr_doc_type_200001" {
		
			grok {
				match => {
					"message" => "%{WORD:roadManagementNumber}\|%{WORD:administrativeCode}\|(?<provinceName>[^|]*)\|(?<districtName>[^|]*)\|(?<neighborhoodName>[^|]*)\|(?<riName>[^|]*)\|%{WORD:roadCode}\|(?<roadName>[^|]*)\|%{WORD:basementStatus}\|%{WORD:buildingNumber}\|%{WORD:buildingSubNumber}\|%{WORD:basicAreaNumber}\|%{WORD:effectiveDate}\|(?<moveReasonCode>[^|]*)\|(?<entranceSerialNumber>[^|]*)\|(?<entranceDivision>[^|]*)\|(?<entranceType>[^|]*)\|(?<entranceCoordX>[^|]*)\|(?<entranceCoordY>[^|]*)"
				}
			}
			
			if [entranceCoordX] {
				mutate {
					replace => ["entranceCoordX", "%{entranceCoordX}"]
					replace => ["entranceCoordY", "%{entranceCoordY}"]
				}
			} else {
				mutate {				
					replace => ["entranceCoordX", 0]
					replace => ["entranceCoordY", 0]
				}
			}

			mutate {
				#split => {"message" => "|"}
				add_field => {
					"log_message_200001" => "%{message}"
					"full_addr_id" => "%{roadManagementNumber}%{roadCode}%{basementStatus}%{buildingNumber}%{buildingSubNumber}"
					"[locations][lat]" => "%{entranceCoordX}"
					"[locations][lon]" => "%{entranceCoordY}"
				}
				remove_field => ["message", "log", "host", "tags", "agent"]
			}
			
		}
	}
}


output {

	file {
		path => "/data/logs/logstash/logstash-%{server_hostname}-full-addr-V1-%{+YYYY-MM-dd}.log"
		codec => line { format => "%{formatted_timestamp} [%{thread_id}] [%{log_type}] [%{full_addr_id}] - %{log_message}" }
	}

	elasticsearch {
		hosts => ["https://elasticsearch.konadc.com:9200"]
		cacert => '/home/elastic/elk/logstash-7.9.1/certs/ca.crt'
		index => "full-addr-v1"
		document_id => "%{[full_addr_id]}"
		user => "elastic"
		password => "Kona!234"
		action => "update"
		doc_as_upsert => true
	}

	stdout {
		codec => rubydebug
	}

}
```