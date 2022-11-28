package com.squaresponge.resource_web.infrastructure.persistence.mongodb

import org.bson.Document
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.data.mongodb.gridfs.GridFsResource
import org.springframework.data.mongodb.gridfs.GridFsTemplate
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class MongoFileService(
    private val gridFsTemplate: GridFsTemplate,
    private val mongoTemplate: MongoTemplate
) {
    fun saveFile(multipartFile: MultipartFile, meta: Map<String, Any>): String {
        val metaData = Document("resourceId", meta)
        val fileId = gridFsTemplate.store(
            multipartFile.inputStream,
            multipartFile.originalFilename,
            multipartFile.contentType,
            metaData
        )
        return fileId.toString()
    }

    fun getFile(fileId: String): GridFsResource {
        val file = gridFsTemplate.findOne(Query(Criteria.where("_id").`is`(fileId)))
        return gridFsTemplate.getResource(file)
    }

    fun deleteFile(resourceId: String) {
        mongoTemplate.updateMulti(
            Query(Criteria.where("metadata.resourceId").`is`(resourceId)),
            Update.update("metadata.remove", true),
            "fs.files"
        )
    }
}