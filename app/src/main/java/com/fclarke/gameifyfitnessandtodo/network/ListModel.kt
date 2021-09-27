package com.fclarke.gameifyfitnessandtodo.network
/*
curl https://api.todoist.com/sync/v8/sync \
-H "Authorization: Bearer todoistKey" \
-d sync_token=* \
-d resource_types='["projects"]'*/
//stuff
//project/name
data class ListModel(val projects: ArrayList<ProjectInfo>, val sync_token :String)
data class ProjectInfo(val name:String, val inbox_project: Boolean, val id: Long)
