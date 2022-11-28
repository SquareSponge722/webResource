package com.squaresponge.resource_web.domain.resource

import com.squaresponge.resource_web.domain.shared.ValueObject

data class Document(val files: List<File> = emptyList()) : ValueObject<Document>