package ca.cmpt362.projects.WeCareApp.diary

import java.sql.Timestamp
import java.util.*

class Constants{
    lateinit var id:String
    lateinit var title:String
    lateinit var entry:String
    lateinit var timestamp: String

    constructor(){
    }

    constructor(id: String, title: String, entry: String, timestamp: String) {
        this.id = id
        this.title = title
        this.entry = entry
        this.timestamp = timestamp
    }


}
