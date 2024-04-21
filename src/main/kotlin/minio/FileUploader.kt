package minio

import io.minio.BucketExistsArgs
import io.minio.MinioClient
import io.minio.UploadObjectArgs
import io.minio.errors.MinioException


class FileUploader {
    fun uploadAsJPGFromPDF(path: String){
        try {
            val client = MinioClient.builder()
                .endpoint("http://192.168.1.163:9000")
                .credentials("MINIO", "SECRET_SECRET").build()
            val bucketExists = client.bucketExists(
                BucketExistsArgs.builder().bucket("pdf-image-bucket")
                    .build()
            )
            if (bucketExists) {
                client.uploadObject(
                    UploadObjectArgs.builder()
                        .bucket("pdf-image-bucket")
                        .`object`("pdf-2024.png")
                        .filename(path)
                        .build()
                )
            }

        } catch (e: MinioException) {
            println(e)
        }
    }
}