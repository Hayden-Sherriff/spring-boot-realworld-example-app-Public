# /articles
### GET /articles/{slug}
Get an article by slug
#### Path Parameters
| Name | Type | Description |
|------|------|-------------|
| slug | String | Path parameter evidenced in controller signature |

#### Query Parameters
None

#### Header Parameters
None

#### Request Body Structure
| Field | Type | Description |
|-------|------|-------------|
| Not evidenced | Unknown | No explicit request payload structure detected |

#### Request Example
```json
{}
```

#### Response Structure
| Field | Type | Description |
|-------|------|-------------|
| Not evidenced | Unknown | No explicit response payload structure detected |

#### Response Example
```json
{}
```

#### Documented Responses
- 200: Article retrieved successfully
- 404: Article not found

# /articles
### PUT /articles/{slug}
Update an article
#### Path Parameters
| Name | Type | Description |
|------|------|-------------|
| slug | String | Path parameter evidenced in controller signature |

#### Query Parameters
None

#### Header Parameters
None

#### Request Body Structure
| Field | Type | Description |
|-------|------|-------------|
| title | String | The title of the article. Default is an empty string. |
| body | String | The body content of the article. Default is an empty string. |
| description | String | A short description or summary of the article. Default is an empty string. |

#### Request Example
```json
{
  "article": {
    "title": "string",
    "body": "string",
    "description": "string"
  }
}
```

#### Response Structure
| Field | Type | Description |
|-------|------|-------------|
| Not evidenced | Unknown | No explicit response payload structure detected |

#### Response Example
```json
{}
```

# /articles
### DELETE /articles/{slug}
Delete an article
#### Path Parameters
| Name | Type | Description |
|------|------|-------------|
| slug | String | Path parameter evidenced in controller signature |

#### Query Parameters
None

#### Header Parameters
None

#### Request Body Structure
| Field | Type | Description |
|-------|------|-------------|
| Not evidenced | Unknown | No explicit request payload structure detected |

#### Request Example
```json
{}
```

#### Response Structure
| Field | Type | Description |
|-------|------|-------------|
| Not evidenced | Unknown | No explicit response payload structure detected |

#### Response Example
```json
{}
```

#### Documented Responses
- 204: Article deleted successfully
- 403: Not authorized to delete the article
- 404: Article not found

# /articles/{slug}
### POST /articles/{slug}/favorite
Favorite an article
#### Path Parameters
| Name | Type | Description |
|------|------|-------------|
| slug | String | Path parameter evidenced in controller signature |

#### Query Parameters
None

#### Header Parameters
None

#### Request Body Structure
| Field | Type | Description |
|-------|------|-------------|
| Not evidenced | Unknown | No explicit request payload structure detected |

#### Request Example
```json
{}
```

#### Response Structure
| Field | Type | Description |
|-------|------|-------------|
| Not evidenced | Unknown | No explicit response payload structure detected |

#### Response Example
```json
{}
```

#### Documented Responses
| Status Code | Description |
|-------------|-------------|
| 200 | Article favorited successfully |
| 401 | Unauthorized - authentication is required |
| 404 | Article not found |
| 500 | Internal server error |

# /articles/{slug}
### DELETE /articles/{slug}/favorite
Unfavorite an article
#### Path Parameters
| Name | Type | Description |
|------|------|-------------|
| slug | String | Path parameter evidenced in controller signature |

#### Query Parameters
None

#### Header Parameters
None

#### Request Body Structure
| Field | Type | Description |
|-------|------|-------------|
| Not evidenced | Unknown | No explicit request payload structure detected |

#### Request Example
```json
{}
```

#### Response Structure
| Field | Type | Description |
|-------|------|-------------|
| Not evidenced | Unknown | No explicit response payload structure detected |

#### Response Example
```json
{}
```

#### Documented Responses
- 200: Article unfavorited successfully
- 401: Unauthorized - authentication is required
- 404: Article not found
- 500: Internal server error

# /articles
### POST /articles
Create an article
#### Path Parameters
None
#### Query Parameters
None
#### Header Parameters
None
#### Request Body Structure
| Field | Type | Description |
|-------|------|-------------|
| title | String | The title of the article. This field is required and must not be blank. |
| description | String | A short description or summary of the article. This field is required and must not be blank. |
| body | String | The main content or body of the article. This field is required and must not be blank. |
| tagList | List<String> | A list of tags associated with the article. This field is optional and may be null or empty. |
#### Request Example
```json
{
  "article": {
    "title": "string",
    "description": "string",
    "body": "string",
    "tagList": []
  }
}
```
#### Response Structure
| Field | Type | Description |
|-------|------|-------------|
| Not evidenced | Unknown | No explicit response payload structure detected |
#### Response Example
```json
{}
```

# /articles
### GET /articles/feed
Get user feed
#### Path Parameters
None
#### Query Parameters
| Name | Type | Description |
|------|------|-------------|
| offset | int | Query parameter evidenced in controller signature |
| limit | int | Query parameter evidenced in controller signature |

#### Header Parameters
None
#### Request Body Structure
| Field | Type | Description |
|-------|------|-------------|
| Not evidenced | Unknown | No explicit request payload structure detected |

#### Request Example
```json
{}
```

#### Response Structure
| Field | Type | Description |
|-------|------|-------------|
| Not evidenced | Unknown | No explicit response payload structure detected |

#### Response Example
```json
{}
```

#### Documented Responses
- 200: Feed retrieved successfully
- 401: Unauthorized

# /articles
### GET /articles
Get articles
#### Path Parameters
None
#### Query Parameters
| Name | Type | Description |
|------|------|-------------|
| offset | int | Query parameter evidenced in controller signature (defaultValue = "0") |
| limit | int | Query parameter evidenced in controller signature (defaultValue = "20") |
| tag | String | Query parameter evidenced in controller signature (required = false) |
| favorited | String | Query parameter evidenced in controller signature (required = false) |
| author | String | Query parameter evidenced in controller signature (required = false) |
#### Header Parameters
None
#### Request Body Structure
| Field | Type | Description |
|-------|------|-------------|
| Not evidenced | Unknown | No explicit request payload structure detected |
#### Request Example
```json
{}
```
#### Response Structure
| Field | Type | Description |
|-------|------|-------------|
| Not evidenced | Unknown | No explicit response payload structure detected |
#### Response Example
```json
{}
```
#### Documented Responses
| Status Code | Description |
|-------------|-------------|
| 200 | Articles retrieved successfully |

# /articles/{slug}
### POST /articles/{slug}/comments
Create a new comment for an article
#### Path Parameters
| Name | Type | Description |
|------|------|-------------|
| slug | String | Path parameter evidenced in controller signature |

#### Query Parameters
None

#### Header Parameters
None

#### Request Body Structure
| Field | Type | Description |
|-------|------|-------------|
| Not evidenced | Unknown | No explicit request payload structure detected |

#### Request Example
```json
{}
```

#### Response Structure
| Field | Type | Description |
|-------|------|-------------|
| Not evidenced | Unknown | No explicit response payload structure detected |

#### Response Example
```json
{}
```

#### Documented Responses
| Status Code | Description |
|-------------|-------------|
| 201 | Comment created |
| 401 | Unauthorized |
| 404 | Article not found |
| 422 | Validation error |

# /articles/{slug}
### GET /articles/{slug}/comments
Get comments for an article
#### Path Parameters
| Name | Type | Description |
|------|------|-------------|
| slug | String | Path parameter evidenced in controller signature |

#### Query Parameters
None

#### Header Parameters
None

#### Request Body Structure
| Field | Type | Description |
|-------|------|-------------|
| Not evidenced | Unknown | No explicit request payload structure detected |

#### Request Example
```json
{}
```

#### Response Structure
| Field | Type | Description |
|-------|------|-------------|
| comments | [] | List of comments returned |

#### Response Example
```json
{
  "comments": []
}
```

#### Documented Responses
- 200: List of comments returned
- 401: Unauthorized
- 404: Article not found

# /articles/{slug}/comments
### DELETE /articles/{slug}/comments/{id}
Delete a comment from an article
#### Path Parameters
| Name | Type | Description |
|------|------|-------------|
| slug | String | Path parameter evidenced in controller signature |
| id | String | Path parameter evidenced in controller signature |

#### Query Parameters
None

#### Header Parameters
None

#### Request Body Structure
| Field | Type | Description |
|-------|------|-------------|
| Not evidenced | Unknown | No explicit request payload structure detected |

#### Request Example
```json
{}
```

#### Response Structure
| Field | Type | Description |
|-------|------|-------------|
| Not evidenced | Unknown | No explicit response payload structure detected |

#### Response Example
```json
{}
```

#### Documented Responses
| HTTP Code | Description |
|-----------|-------------|
| 204 | Comment deleted |
| 401 | Unauthorized |
| 403 | Forbidden |
| 404 | Article or comment not found |

# /user
### GET /user
Get current user
#### Path Parameters
None
#### Query Parameters
None
#### Header Parameters
| Name | Type | Description |
|------|------|-------------|
| Authorization | String | Evidenced header parameter in controller signature |
#### Request Body Structure
| Field | Type | Description |
|-------|------|-------------|
| Not evidenced | Unknown | No explicit request payload structure detected |
#### Request Example
```json
{}
```
#### Response Structure
| Field | Type | Description |
|-------|------|-------------|
| Not evidenced | Unknown | No explicit response payload structure detected |
#### Response Example
```json
{}
```

# /user
### PUT /user
Update current user's profile
#### Path Parameters
None
#### Query Parameters
None
#### Header Parameters
| Name | Type | Description |
|------|------|-------------|
| Authorization | String | Header parameter evidenced in controller signature |
#### Request Body Structure
| Field | Type | Description |
|-------|------|-------------|
| user.email | String | The user's email address. Defaults to an empty string when not provided. This field is validated to conform to an email format. |
| user.password | String | The user's password. Defaults to an empty string when not provided. |
| user.username | String | The user's username. Defaults to an empty string when not provided. |
| user.bio | String | The user's biography or profile description. Defaults to an empty string when not provided. |
| user.image | String | The URL or reference to the user's profile image. Defaults to an empty string when not provided. |
#### Request Example
```json
{
  "user": {
    "email": "string",
    "password": "string",
    "username": "string",
    "bio": "string",
    "image": "string"
  }
}
```
#### Response Structure
| Field | Type | Description |
|-------|------|-------------|
| Not evidenced | Unknown | No explicit response payload structure detected |
#### Response Example
```json
{}
```
#### Documented Responses
| HTTP Code | Description |
|-----------|-------------|
| 200 | OK |
| 400 | Bad Request - validation failed |
| 401 | Unauthorized |
| 404 | User not found |

# /profiles
### GET /profiles/{username}
Get profile by username
#### Path Parameters
| Name | Type | Description |
|------|------|-------------|
| username | String | Path parameter evidenced in controller signature |

#### Query Parameters
None

#### Header Parameters
None

#### Request Body Structure
| Field | Type | Description |
|-------|------|-------------|
| Not evidenced | Unknown | No explicit request payload structure detected |

#### Request Example
```json
{}
```

#### Response Structure
| Field | Type | Description |
|-------|------|-------------|
| Not evidenced | Unknown | No explicit response payload structure detected |

#### Response Example
```json
{}
```

#### Documented Responses
- 200: Profile retrieved successfully
- 404: Profile not found

# /profiles/{username}
### POST /profiles/{username}/follow
Follow user
#### Path Parameters
| Name | Type | Description |
|------|------|-------------|
| username | String | Path parameter evidenced in controller signature |

#### Query Parameters
None

#### Header Parameters
None

#### Request Body Structure
| Field | Type | Description |
|-------|------|-------------|
| Not evidenced | Unknown | No explicit request payload structure detected |

#### Request Example
```json
{}
```

#### Response Structure
| Field | Type | Description |
|-------|------|-------------|
| Not evidenced | Unknown | No explicit response payload structure detected |

#### Response Example
```json
{}
```

#### Documented Responses
| Status Code | Description |
|-------------|-------------|
| 200 | Followed user successfully |
| 404 | Target user not found |

# /profiles/{username}
### DELETE /profiles/{username}/follow
Unfollow user
#### Path Parameters
| Name | Type | Description |
|------|------|-------------|
| username | String | Path parameter evidenced in controller signature |

#### Query Parameters
None

#### Header Parameters
None

#### Request Body Structure
| Field | Type | Description |
|-------|------|-------------|
| Not evidenced | Unknown | No explicit request payload structure detected |

#### Request Example
```json
{}
```

#### Response Structure
| Field | Type | Description |
|-------|------|-------------|
| Not evidenced | Unknown | No explicit response payload structure detected |

#### Response Example
```json
{}
```

#### Documented Responses
| Status Code | Description |
|-------------|-------------|
| 200 | Unfollowed user successfully |
| 404 | Target user or follow relation not found |

# /tags
### GET /tags
Get all tags
#### Path Parameters
None
#### Query Parameters
None
#### Header Parameters
None
#### Request Body Structure
| Field | Type | Description |
|-------|------|-------------|
| Not evidenced | Unknown | No explicit request payload structure detected |

#### Request Example
```json
{}
```

#### Response Structure
| Field | Type | Description |
|-------|------|-------------|
| tags | [] | Field evidenced in controller response map |

#### Response Example
```json
{
  "tags": []
}
```

#### Documented Responses
| Status Code | Description |
|-------------|-------------|
| 200 | OK |
| 500 | Internal Server Error |