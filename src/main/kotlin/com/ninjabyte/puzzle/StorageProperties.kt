package com.ninjabyte.puzzle

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@ConfigurationProperties("storage")
@Component
class StorageProperties {

    /**
     * Folder location for storing files
     */
    var location: String = "photos1"


}