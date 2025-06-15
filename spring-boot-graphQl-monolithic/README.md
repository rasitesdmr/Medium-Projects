# createUser

 ```graphql
mutation{
    createUser(userCreateRequest:{
        firstName: "Raşit",
        lastName: "Eşdemir",
        email: "msmsmmdd@gmail.com",
        password: "1",
        userAuthRole : ADMIN
    }){
        id,
        firstName,
        lastName,
        email,
        userAuthRole,
        createDate,
        updateDate
    }
}
```

# getAllUserResponses
 ```graphql
query {
    getAllUserResponses(pageNo: 0, pageSize: 3) {
        content {
            id
            firstName
            lastName
            email
            userAuthRole
            createDate
            updateDate
        }
        totalPages
        totalElements
        number
        size
    }
}

```

# createPhoto
 ```graphql
mutation{
    createPhoto(photoCreateRequest: {
        userId: 1,
        imageUrl: "https://github.com/danvega/graphql-paging/blob/master/src/main/java/dev/danvega/graphqlpaging/repository/PersonRepository.java"
    }){
        id,
        imageUrl
        createDate
        updateDate
    }
}
```


# createLike
 ```graphql
mutation{
    createLike(likeCreateRequest: {
        userId: 1
        photoId: 1
    }){
        id
        createDate
        updateDate
    }
}
```


# getAllLikeResponseByUserId
 ```graphql
query{
    getAllLikeResponseByUserId(userId: 1){
        id
        createDate
        updateDate
    }
}
```


# createComment
 ```graphql
mutation{
    createComment(commentCreateRequest: {
        text: "Resim güzel"
        userId: 1
        photoId: 1
    }){
        id
        text
        createDate
        updateDate
    }
}
```

# getUserDetailsResponseById
 ```graphql
query{
    getUserDetailsResponseById(id: 1){
        userResponse{
            id
            firstName
            lastName
            email
        }
        photos(page: 0, size: 5){
            content{
                photo{
                    id
                    imageUrl
                    createDate
                    updateDate
                }
                comments(page: 0, size: 10){
                    content{
                        id
                        text
                        createDate
                        updateDate
                    }
                    totalPages
                    totalElements
                }
                likes(page: 0, size: 10){
                    content{
                        id
                        createDate
                        updateDate
                    }
                    totalPages
                    totalElements
                }
            }
        }
    }
}
```