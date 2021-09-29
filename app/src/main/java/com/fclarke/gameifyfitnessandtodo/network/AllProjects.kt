package com.fclarke.gameifyfitnessandtodo.network
/*
curl https://api.todoist.com/sync/v8/sync \
-H "Authorization: Bearer todoistKey" \
-d sync_token=* \
-d resource_types='["projects"]'

  "projects": [
    {
      "child_order": 0,
      "collapsed": 0,
      "color": 48,
      "id": 942035715,
      "inbox_project": true,
      "is_archived": 0,
      "is_deleted": 0,
      "is_favorite": 0,
      "legacy_id": 136729347,
      "name": "Inbox",
      "parent_id": null,
      "shared": false,
      "sync_id": null
    },
*/

data class AllProjects(val projects: ArrayList<ProjectsArray>, val sync_token :String)
data class ProjectsArray(val name:String, val inbox_project: Boolean, val id: Long)
/*
curl https://api.todoist.com/sync/v8/projects/get_data \
-H "Authorization: Bearer 0123456789abcdef0123456789abcdef01234567" \
-d project_id=128501682
*/
/*project
"items": [
{
    "added_by_uid": 3579461,
    "assigned_by_uid": null,
    "checked": 0,
    "child_order": 1,
    "collapsed": 0,
    "content": "Buy Milk",
    "date_added": "2021-09-25T04:51:00Z",
    "date_completed": null,
    "description": "",
    "due": {
        "date": "2021-09-24",
        "is_recurring": false,
        "lang": "en",
        "string": "today",
        "timezone": null
    },
    "is_deleted": 0,
    "labels": [],
    "priority": 1,
    "section_id": null,
    "sync_id": null,
},
"sync_token": "yYZWT5PfHh9S2d3tGlbjD4diF_kkaVUepT2K48LKN2OpPGOYluZLTayYCqaVl78X0ZSoJsFCd2cQfUFTjUF0O1MA4pgiCR54t6JxicMNOEp7",
sections

 */

data class ProjectModel(val projects: ArrayList<ItemsArray>, val sync_token :String)
data class ItemsArray(val content:String, val description:String, val date_completed: String?, val is_deleted: Int, val priority: Int)
