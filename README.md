# Vodafone Device APIs
*Author* : *Sibendra Pratap Singh ; sibendra30singh@gmail.com*

This is a Java-Springboot based project containing APIs to fetch devices, update device configuration and delete device.

## API Details
1. *GET /device* - This API will return the paginated response for the given sim_status.
| Param | Type | Default |
| -- | -- | -- | -- |
|simStatus|String|Active|
|pageSize|Integer|10|
|pageNumber|Integer|1|

### Response Body:
#### Device
| Attribute Name | Type |
| -- | -- |
|id|String|
|name|String|
|description|String|
|status|String (READY/NOT_READY|
|createdDate|Date|
|lastModifiedDate|Date|

### Sim
| Attribute Name | Type |
| -- | -- |
|id|String|
|operatorCode|String|
|country|String|
|status|String (READY/NOT_READY|
|createdDate|Date|
|lastModifiedDate|Date|

2. *GET /order* - This API will return all the orders with item details and pricing information.
Sample Response Body:
`[{"id":10000,"customerId":1234,"items":[{"itemId":1001,"itemName":"Apple","qty":5,"amount":1.8}],"totalAmount":1.8},{"id":10001,"customerId":1234,"items":[{"itemId":1001,"itemName":"Apple","qty":7,"amount":2.4}],"totalAmount":2.4}]`
3. *GET /order/{orderId}* - This API will return the order with given order Id with item details and pricing information.
Sample Response Body:
`{"id":10000,"customerId":1234,"items":[{"itemId":1001,"itemName":"Apple","qty":5,"amount":1.8}],"totalAmount":1.8}`

