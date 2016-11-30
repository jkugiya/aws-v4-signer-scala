
#aws-v4-signer-scala

Ported from [aws-v4-signer-java](https://github.com/lucasweb78/aws-v4-signer-java)
aws-v4-signer-java is a lightweight, zero-dependency implementation of the AWS V4 signing algorithm required by many of the AWS services.

## Setup

Add the latest aws-v4-signer-scala dependency to your project

```
  "com.github.jkugiya" %% "aws-v4-signer-scala" % "0.1"
```

## Usage

### S3

```scala
val contentSha256 = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"
val request = HttpRequest("GET", new URI("https://examplebucket.s3.amazonaws.com?max-keys=2&prefix=J"))
val signer = Signer(region = "us-east-1", service = "s3", AwsCredentials(ACCESS_KEY, SECRET_KEY))

val signature =
  signer.sign(
    request,
    contentSha256,
    Header("Host", "examplebucket.s3.amazonaws.com"),
    Header("x-amz-date", "20130524T000000Z"),
    Header("x-amz-content-sha256", contentSha256))
```

### Glacier

```scala
val contentSha256 = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"
val request = HttpRequest("PUT", new URI("https://glacier.us-east-1.amazonaws.com/-/vaults/examplevault"))
val signer = Signer(region = "us-east-1", service = "glacier", AwsCredentials(ACCESS_KEY, SECRET_KEY))

val signature =
  signer.sign(
    request,
    contentSha256,
    Header("Host", "glacier.us-east-1.amazonaws.com"),
    Header("x-amz-date", "20120525T002453Z"),
    Header("x-amz-glacier-version", "2012-06-01"))
```
