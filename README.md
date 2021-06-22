# Vodafone Device APIs
*Author* : *Sibendra Pratap Singh ; sibendra30singh@gmail.com*

This is a Java-Springboot based project containing APIs to fetch devices, update device configuration and delete device.

## How to test the project ?
*mvn clean test*

## How to run integration test ?
*mvn clean verify -Dspring.profiles.active=IT*

## How to run the project
*mvn spring-boot:run*

## Pre-Configured Products:
*src/main/resources* directory contains device.catalog.json and sim.catalog.json which are used to pre-loads the data in to DEVICE and SIM table.

#### DEVICE table:
| Column | Type | Description |
| -- | -- | -- |
| id | String | This is unique Id of device |
| name | String | This is name of device |
| description | String | This contains the short description of device |
| status | String | status of device |
| createdDate | String | Created date |
| lastModifiedDate | String | Last modified date |
| simId | String | Foreign key from SIM table |

#### SIM table:
| Column | Type | Description |
| -- | -- | -- |
| id | String | This is unique Id of device |
| operatorCode | String | Operator Code |
| country | String | Country |
| status | String | status of device |
| createdDate | String | Created date |
| lastModifiedDate | String | Last modified date |

## API Details
1. *GET /device* - This API will return the paginated response for the given sim_status.

| Param | Type | Default | Valid Values |
| -- | -- | -- | -- |
| simStatus | String | Active | Active/Waiting_for_activation/Deactivated/Blocked |
| pageSize | Integer | 10 | - |
| pageNumber | Integer | 1 | - |

### Response Body:
#### Device

| Attribute Name | Type |  Valid Values |
| -- | -- | -- |
|id|String|
|name|String| - |
|description|String| - |
|status|String | (READY/NOT_READY) |
|sim|Sim | - |
|createdDate|Date| - |
|lastModifiedDate|Date| - |

#### Sim
| Attribute Name | Type |  Valid Values |
| -- | -- | -- |
|id|String| - |
|operatorCode|String|-|
|country|String|-|
|status|String|(Active/Waiting_for_activation/Deactivated/Blocked)|
|createdDate|Date|-|
|lastModifiedDate|Date|-|

2. *PATCH /device/{deviceId}* - This API will be used to update the configuration status of device.
#### Sample request body:
`{"status": "READY"}`

3. *DELETE /device/{deviceId}* - This API will be used to remove device with given device-id.

*Please refer to api.spec.yaml for more details.*
