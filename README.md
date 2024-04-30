
# Ecommerce API

An API created to simulate all funcionalities of an Ecommerce store. You will be able to create and login into your account, search and add products to the cart and finish shopping.



## Demonstration

You can test yourself using the IPv4: 54.210.75.235:8080 
or the DNS: ec2-54-210-75-235.compute-1.amazonaws.com

![Postman request demo](https://testecommercebucket.s3.amazonaws.com/imagem_2024-04-29_155121221.png)



## Tools utilized

**Back-end:** Java, Spring boot, DynamoDB, SQS

**Dev-ops** GIT, Github actions, Docker, EC2


## Functionalities

- Account sign up and sign in 
- Add and remove product from the cart, find a cart using the userId or the cartId and delete
- Create, find and delete an order
- Create, find and delete a payment
- Create, find and delete an address



## Environment variables

To run this project you will need to add the following env variables:

Need to create an SQS Queue on AWS before:
`SQS_ADD_TO_CART_QUEUE_URL`

Need to create an SQS Queue on AWS before:
`SQS_REFUND_CART_QUEUE_URL`

Your keys to access the AWS:         
`AWS_ACCESS_KEY_ID`

`AWS_SECRET_ACCESS_KEY`
## API documentation

![User controller swagger](https://testecommercebucket.s3.amazonaws.com/imagem_2024-04-30_114836500.png)

#### Register a new user

```http
  POST /api/users/register
```

| Body   | Type       | Description                          |
| :---------- | :--------- | :---------------------------------- |
| `user` | `User` | **Required:** User object to register  | 

#### Login a User

```http
  POST /api/users/login
```

| Parameter   | Type       | Description                          |
| :---------- | :--------- | :---------------------------------- |
| `user` | `User` | **Required:** User object to be log in |


#### Find a User by ID

```http
  GET /api/users/{id}
```

| Parameter   | Type       | Description                          |
| :---------- | :--------- | :---------------------------------- |
| `id` | `String` | **Required:** ID of the user to be found |


#### Update a User

```http
  PUT /api/users/edit/{id}
```

| Parameter   | Type       | Description                          |
| :---------- | :--------- | :---------------------------------- |
| `user` | `User` | **Required:** User object in the body with updated fields |
| `id` | `String` | **Required:** ID of the user to be updated |


#### Delete a User

```http
  DELETE /api/users/delete/{id}
```

| Parameter   | Type       | Description                          |
| :---------- | :--------- | :---------------------------------- |
| `id` | `String` | **Required:** ID of the user to be deleted |

![Product controller swagger](https://testecommercebucket.s3.amazonaws.com/imagem_2024-04-30_114912609.png)


#### Create a Product

```http
  POST /api/products
```

| Parameter   | Type       | Description                          |
| :---------- | :--------- | :---------------------------------- |
| `product` | `Product` | **Required:** Product object to be created |


#### Find a Product by ID

```http
  GET /api/products/id/{productId}
```

| Parameter   | Type       | Description                          |
| :---------- | :--------- | :---------------------------------- |
| `productId` | `String` | **Required:** ID of the product to be found |


#### Find All Products (Pageable)

```http
   GET /api/products
```

| Parameter   | Type       | Description                          |
| :---------- | :--------- | :---------------------------------- |
| `pageNumber	` | `Integer` | **Optional:** Page number (default: 0) |
| `pageSize	` | `Integer` | **Optional:** Page size (default: 10) |

#### Find Products by Name

```http
   GET /api/products/name/{name}
```

| Parameter   | Type       | Description                          |
| :---------- | :--------- | :---------------------------------- |
| `name` | `String` | **Required:** Name of the products to be found |

#### Delete a Product

```http
   DELETE /api/products/delete/{productId}
```

| Parameter   | Type       | Description                          |
| :---------- | :--------- | :---------------------------------- |
| `productId` | `String` | **Required:** ID of the product to be deleted |

![Payment Method controller swagger](https://testecommercebucket.s3.amazonaws.com/imagem_2024-04-30_114936254.png)

#### Create a Payment Method

```http
   POST /api/payments
```

| Parameter   | Type       | Description                          |
| :---------- | :--------- | :---------------------------------- |
| `paymentMethod` | `PaymentMethod` | **Required:** PaymentMethod object to be created |

#### Find a Payment Method by ID

```http
   GET /api/payments/{paymentId}
```

| Parameter   | Type       | Description                          |
| :---------- | :--------- | :---------------------------------- |
| `paymentId	` | `String` | **Required:** ID of the paymentMethod to be found |

#### Delete a Payment Method

```http
   DELETE /api/payments/delete/{paymentId}
```

| Parameter   | Type       | Description                          |
| :---------- | :--------- | :---------------------------------- |
| `paymentId` | `String` | **Required:** ID of the paymentMethod to be deleted |

![Order controller swagger](https://testecommercebucket.s3.amazonaws.com/imagem_2024-04-30_114949713.png)

#### Create an Order

```http
   POST /api/orders
```

| Parameter   | Type       | Description                          |
| :---------- | :--------- | :---------------------------------- |
| `order` | `Order` | **Required:** Order object to be created |

#### Find an Order by ID

```http
    GET /api/orders/{orderId}
```

| Parameter   | Type       | Description                          |
| :---------- | :--------- | :---------------------------------- |
| `orderId` | `String` | **Required:** ID of the order to be found |

#### Delete an Order

```http
   DELETE /api/orders/delete/{orderId}
```

| Parameter   | Type       | Description                          |
| :---------- | :--------- | :---------------------------------- |
| `orderId` | `String` | **Required:** ID of the order to be deleted |

![Address controller swagger](https://testecommercebucket.s3.amazonaws.com/imagem_2024-04-30_115017468.png)

#### Create an Order

```http
   POST /api/address
```

| Parameter   | Type       | Description                          |
| :---------- | :--------- | :---------------------------------- |
| `address` | `Order` | **Required:** Address object to be created |

#### Find an Address by ID

```http
    GET /api/address/{addressId}
```

| Parameter   | Type       | Description                          |
| :---------- | :--------- | :---------------------------------- |
| `addressId` | `String` | **Required:** ID of the address to be found |

#### Delete an Address

```http
    DELETE /api/address/delete/{addressId}
```

| Parameter   | Type       | Description                          |
| :---------- | :--------- | :---------------------------------- |
| `addressId` | `String` | **Required:** ID of the address to be deleted |


![Cart controller swagger](https://testecommercebucket.s3.amazonaws.com/imagem_2024-04-30_115007567.png)

#### Add Product to Cart

```http
   POST /api/carts/addProduct
```

| Parameter   | Type       | Description                          |
| :---------- | :--------- | :---------------------------------- |
| `request` | `AddToCartMessage` | **Required:** AddToCartMessage object containing productId, userId, and quantity |

#### Refund Product from Cart back to stock

```http
    POST /api/carts/refundProduct
```

| Parameter   | Type       | Description                          |
| :---------- | :--------- | :---------------------------------- |
| `refundItemsList` | `List<RefundCartMessage>` | **Required:** List of RefundCartMessage objects containing productId, userId, and quantity |

#### Find a Cart by Cart ID

```http
   GET /api/carts/findByCart/{cartId}
```

| Parameter   | Type       | Description                          |
| :---------- | :--------- | :---------------------------------- |
| `cartId` | `String` | **Required:** ID of the cart to be found |

#### Find a Cart by Cart ID

```http
   GET /api/carts/findByCart/{cartId}
```

| Parameter   | Type       | Description                          |
| :---------- | :--------- | :---------------------------------- |
| `cartId` | `String` | **Required:** ID of the cart to be found |

#### Find a Cart by User ID

```http
   GET /api/carts/findByUser/{userId}
```

| Parameter   | Type       | Description                          |
| :---------- | :--------- | :---------------------------------- |
| `userId` | `String` | **Required:** ID of the user to find the cart for |

#### Delete a Cart by ID

```http
   DELETE /api/carts/delete/{cartId}
```

| Parameter   | Type       | Description                          |
| :---------- | :--------- | :---------------------------------- |
| `cartId` | `String` | **Required:** ID of the cart to be deleted |

