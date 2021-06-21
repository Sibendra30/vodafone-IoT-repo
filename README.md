# Vodafone Device APIs
*Author* : *Sibendra Pratap Singh ; sibendra30singh@gmail.com*

This is a Java-Springboot based project containing APIs to fetch devices, update device configuration and delete device.

## API Details
1. *GET /device* - This API will return the paginated response for the given sim_status.

| Param | Type | Default | Valid Values |
| -- | -- | -- | -- |
| simStatus | String | Active | Active/Waiting for activation/Deactivated/Blocked |
| pageSize | Integer | 10 | - |
| pageNumber | Integer | 1 | - |

### Response Body:
#### Device
| Attribute Name | Type |  Valid Values |
| -- | -- |
|id|String|
|name|String| - |
|description|String| - |
|status|String | (READY/NOT_READY) |
|createdDate|Date| - |
|lastModifiedDate|Date| - |

### Sim
| Attribute Name | Type |  Valid Values |
| -- | -- |
|id|String|
|operatorCode|String|-|
|country|String|-|
|status|String|(Active/Waiting for activation/Deactivated/Blocked)|
|createdDate|Date|-|
|lastModifiedDate|Date|-|
