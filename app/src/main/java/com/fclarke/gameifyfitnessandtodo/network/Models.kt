package com.fclarke.gameifyfitnessandtodo.network

data class AllCompletedItems(val items: ArrayList<CompletedItem>)
data class CompletedItem(var content: String)
data class CompletedProject(val name: String)

/*
Get all completed items
curl https://api.todoist.com/sync/v8/completed/get_all \
-H "Authorization: Bearer 0123456789abcdef0123456789abcdef01234567"
	-d since="2021-09-29T20:16:09Z"
{
    "items": [
    {
        "completed_date": "2021-09-29T20:17:49Z",
        "content": "G",
        "id": 4808118769,
        "legacy_project_id": 136729349,
        "meta_data": null,
        "project_id": 942035717,
        "task_id": 5199174852,
        "user_id": 3579461
    },
    {
        "completed_date": "2021-09-29T20:17:48Z",
        "content": "G",
        "id": 4808118723,
        "legacy_project_id": 136729349,
        "meta_data": null,
        "project_id": 942035717,
        "task_id": 5199174846,
        "user_id": 3579461
    },



*/




data class ProjectModel(val project: Project, val items: ArrayList<Items>)
data class Project(val name: String, val is_deleted: Boolean)
data class Items(
    val content: String,
    val description: String,
    val date_completed: String?,
    val date_added: String,
    val sync_id: Int,
    val priority: Int,
    val is_deleted: Int,
    val in_history: Int,//Whether the task has been marked as completed and is marked to be moved to history, because all the child tasks of its parent are also marked as completed (where 1 is true and 0 is false)
    val checked: Int
)//Whether the task is marked as completed (where 1 is true and 0 is false). //Item = Task

