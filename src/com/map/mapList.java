package com.map;

import java.util.*;

public class mapList {
    private String[] TaskForces;
    private List<String>  Tags;
    private List<String>  Triggers;
    private String[]  TeamTypes;
    private String[]  ScriptTypes;
    private List<String>  AITriggerTypes;
    private List<String>  Events;
    private List<String>  Actions;
    private List<Script>  scripts;
    private List<Map<String,String>> TaskForcesName;
    private List<Map<String,String>>  TagsName;
    private List<Map<String,String>>  TriggersName;
    private List<Map<String,String>>  TeamTypesName;
    private List<Map<String,String>>  ScriptTypesName;
    private List<Map<String,String>>  AITriggerTypesName;
    private List<Map<String,String>> TagsTriggers;//key是tag，value是trigger
    private List<Map<String,String>> Terrains;
    private List<Map<String,String>> Waypoint;
    private String[] Countries;
    String maxnormalcode;
    int row_TaskLineList;
    int row_ScriptLineList;
    int row_TagLineList;
    int row_TriggerLineList;
    int row_AITriggerLineList;
    int row_EventLineList;
    int row_ActionLineList;
    int row_TeamTypeLineList;
    int maxtaskForce;
    int maxTeamType;
    int maxScriptType;
    int width;
    int height;

    public void initialize()
    {
        this.Tags=new ArrayList<>();
        this.scripts=new ArrayList<>();
        this.TaskForces=new String[10000];
        this.Triggers=new ArrayList<>();
        this.TeamTypes=new String[10000];
        this.ScriptTypes=new String[10000];
        this.AITriggerTypes=new ArrayList<>();
        this.TagsName=new ArrayList<>();
        this.Events=new ArrayList<>();
        this.Actions=new ArrayList<>();
        this.TaskForcesName=new ArrayList<>();
        this.TriggersName=new ArrayList<>();
        this.TeamTypesName=new ArrayList<>();
        this.ScriptTypesName=new ArrayList<>();
        this.AITriggerTypesName=new ArrayList<>();
        this.Countries=new String[70];
        this.Terrains=new ArrayList<>();
        this.Waypoint=new ArrayList<>();
    }

    public List<Script> getScripts() {
        return scripts;
    }

    public void setScripts(List<Script> scripts) {
        this.scripts = scripts;
    }

    public List<Map<String, String>> getTerrains() {
        return Terrains;
    }

    public void setTerrains(List<Map<String, String>> terrains) {
        Terrains = terrains;
    }

    public List<Map<String, String>> getWaypoint() {
        return Waypoint;
    }

    public void setWaypoint(List<Map<String, String>> waypoint) {
        Waypoint = waypoint;
    }

    public int getMaxtaskForce() {
        int i,h=this.TaskForces.length,max = -1;
        for(i=0;i<h;i++) {
            if (this.TaskForces[i] != null && this.TaskForces[i].length() > 0) max = i;
        }
        setMaxtaskForce(max);
        return max;
    }

    public void setMaxtaskForce(int maxtaskForce) {
        this.maxtaskForce = maxtaskForce;
    }

    public int getMaxTeamType() {
        int i,h=this.TeamTypes.length,max = -1;
        for(i=0;i<h;i++) {
            if (this.TeamTypes[i] != null && this.TeamTypes[i].length() > 0) max = i;
        }
        setMaxTeamType(max);
        return max;
    }
    public String getMaxTriggerCode() {
        int i,h=this.Triggers.size(),max = -1;
        return this.Triggers.get(h-1);
    }

    public void setMaxTeamType(int maxTeamType) {
        this.maxTeamType = maxTeamType;
    }

    public int getMaxScriptType() {
        int i,h=this.ScriptTypes.length,max = -1;
        for(i=0;i<h;i++) {
            if (this.ScriptTypes[i] != null && this.ScriptTypes[i].length() > 0) max = i;
        }
        setMaxScriptType(max);
        return max;
    }

    public void setMaxScriptType(int maxScriptType) {
        this.maxScriptType = maxScriptType;
    }

    public String getMaxnormalcode() {
        return maxnormalcode;
    }

    public void setMaxnormalcode(String maxnormalcode) {
        this.maxnormalcode = maxnormalcode;
    }

    public void addTags(String s)
    {
        this.Tags.add(s);
    }
    public void addTriggers(String s)
    {
        this.Triggers.add(s);
    }
    public void addEvents(String s)
    {
        this.Events.add(s);
    }
    public void addActions(String s)
    {
        this.Actions.add(s);
    }
    public void addAITriggerTypes(String s)
    {
        this.AITriggerTypes.add(s);
    }
    public void addCountries(String s,int i)
    {
        this.Countries[i]=s;
    }

    public boolean inTaskForces(String code)
    {
        if (code.length()<=0) return false;
        int h=this.TaskForces.length;
        for (int i=0;i<h;i++)
        {
            if (this.TaskForces[i]!=null&&this.TaskForces[i].equals(code)) return true;
        }
        return false;
    }
    public boolean inScriptTypes(String code)
    {
        if (code.length()<=0) return false;
        int h=this.ScriptTypes.length;
        for (int i=0;i<h;i++)
        {
            if (this.ScriptTypes[i]!=null&&this.ScriptTypes[i].equals(code)) return true;
        }
        return false;
    }
    public boolean inTeamTypes(String code)
    {
        if (code.length()<=0) return false;
        int h=this.TeamTypes.length;
        for (int i=0;i<h;i++)
        {
            if (this.TeamTypes[i]!=null&&this.TeamTypes[i].equals(code)) return true;
        }
        return false;
    }
    public int getTriggerPosition(String s,String fuzz)
    {
        int i=0;

        List<Map<String,String>> list=this.TriggersName;
        for(Map<String,String> map:list)
        {
            for  (String k : map.keySet()){
                String code=k.substring(1);
                if(Integer.parseInt(code)>=Integer.parseInt(s.substring(1))&&(map.get(k).contains(fuzz)||fuzz.length()<1))
                {
                    return i;
                }
                i++;
            }
        }
        return -1;
    }
    public int getTeamPosition(String s) {
        int i=0;
        List<String> list= Arrays.asList(this.TeamTypes).subList(0,this.getTeamLength());
        Collections.sort(list);
        for(i=0;i<list.size();i++)
        {
            if (list.get(i).equals(s)) return i;
        }
        return -1;
    }
    public int getScriptPosition(String s) {
        int i=0;
        List<String> list= Arrays.asList(this.ScriptTypes).subList(0,this.getScriptLength());
        Collections.sort(list);
        for(i=0;i<list.size();i++)
        {
            if (list.get(i).equals(s)) return i;
        }
        return -1;
    }
    public int getTeamLength()
    {
        int i=0;
        for(i=0;i<this.getTeamTypes().length;i++)
        {
            if (this.getTeamTypes()[i]==null) return i;

        }
        return i;
    }
    public int getScriptLength()
    {
        int i=0;
        for(i=0;i<this.getScriptTypes().length;i++)
        {
            if (this.getScriptTypes()[i]==null) return i;

        }
        return i;
    }
    public int getTaskForceLength()
    {
        int i=0;
        for(i=0;i<this.getTaskForces().length;i++)
        {
            if (this.getTaskForces()[i]==null) return i;

        }
        return i;
    }
    public List<String> getEvents() {
        return Events;
    }

    public void setEvents(List<String> events) {
        Events = events;
    }

    public List<String> getActions() {
        return Actions;
    }

    public void setActions(List<String> actions) {
        Actions = actions;
    }

    public int getRow_EventLineList() {
        return row_EventLineList;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setRow_EventLineList(int row_EventLineList) {
        this.row_EventLineList = row_EventLineList;
    }

    public int getRow_ActionLineList() {
        return row_ActionLineList;
    }

    public void setRow_ActionLineList(int row_ActionLineList) {
        this.row_ActionLineList = row_ActionLineList;
    }

    public String[] getTaskForces() {
        return TaskForces;
    }

    public void setTaskForces(String[] taskForces) {
        TaskForces = taskForces;
    }

    public String[] getTeamTypes() {
        return TeamTypes;
    }

    public void setTeamTypes(String[] teamTypes) {
        TeamTypes = teamTypes;
    }

    public String[] getScriptTypes() {
        return ScriptTypes;
    }

    public void setScriptTypes(String[] scriptTypes) {
        ScriptTypes = scriptTypes;
    }

    public List<String> getTags() {
        return Tags;
    }

    public void setTags(List<String> tags) {
        Tags = tags;
    }

    public List<String> getTriggers() {
        return Triggers;
    }

    public void setTriggers(List<String> triggers) {
        Triggers = triggers;
    }


    public List<String> getAITriggerTypes() {
        return AITriggerTypes;
    }

    public void setAITriggerTypes(List<String> AITriggerTypes) {
        this.AITriggerTypes = AITriggerTypes;
    }

    public int getRow_TaskLineList() {
        return row_TaskLineList;
    }

    public void setRow_TaskLineList(int row_TaskLineList) {
        this.row_TaskLineList = row_TaskLineList;
    }

    public List<Map<String, String>> getTaskForcesName() {
        return TaskForcesName;
    }

    public void setTaskForcesName(List<Map<String, String>> taskForcesName) {
        TaskForcesName = taskForcesName;
    }

    public List<Map<String, String>> getTagsName() {
        return TagsName;
    }

    public void setTagsName(List<Map<String, String>> tagsName) {
        TagsName = tagsName;
    }

    public List<Map<String, String>> getTriggersName() {
        return TriggersName;
    }

    public void setTriggersName(List<Map<String, String>> triggersName) {
        TriggersName = triggersName;
    }

    public List<Map<String, String>> getTeamTypesName() {
        return TeamTypesName;
    }

    public void setTeamTypesName(List<Map<String, String>> teamTypesName) {
        TeamTypesName = teamTypesName;
    }

    public List<Map<String, String>> getScriptTypesName() {
        return ScriptTypesName;
    }

    public void setScriptTypesName(List<Map<String, String>> scriptTypesName) {
        ScriptTypesName = scriptTypesName;
    }

    public List<Map<String, String>> getAITriggerTypesName() {
        return AITriggerTypesName;
    }

    public void setAITriggerTypesName(List<Map<String, String>> AITriggerTypesName) {
        this.AITriggerTypesName = AITriggerTypesName;
    }

    public int getRow_ScriptLineList() {
        return row_ScriptLineList;
    }

    public void setRow_ScriptLineList(int row_ScriptLineList) {
        this.row_ScriptLineList = row_ScriptLineList;
    }

    public int getRow_TagLineList() {
        return row_TagLineList;
    }

    public void setRow_TagLineList(int row_TagLineList) {
        this.row_TagLineList = row_TagLineList;
    }

    public int getRow_TriggerLineList() {
        return row_TriggerLineList;
    }

    public void setRow_TriggerLineList(int row_TriggerLineList) {
        this.row_TriggerLineList = row_TriggerLineList;
    }

    public int getRow_AITriggerLineList() {
        return row_AITriggerLineList;
    }

    public void setRow_AITriggerLineList(int row_AITriggerLineList) {
        this.row_AITriggerLineList = row_AITriggerLineList;
    }

    public int getRow_TeamTypeLineList() {
        return row_TeamTypeLineList;
    }

    public void setRow_TeamTypeLineList(int row_TeamTypeLineList) {
        this.row_TeamTypeLineList = row_TeamTypeLineList;
    }

    public String[] getCountries() {
        return Countries;
    }

    public void setCountries(String[] countries) {
        Countries = countries;
    }

    public List<Map<String, String>> getTagsTriggers() {
        return TagsTriggers;
    }

    public void setTagsTriggers(List<Map<String, String>> tagsTriggers) {
        TagsTriggers = tagsTriggers;
    }




    public void addTaskForces(int number, String code) {
        this.TaskForces[number]=code;
    }

    public void addTeamTypes(int number, String code) {
        this.TeamTypes[number]=code;
    }

    public void addScriptTypes(int number, String code) {
        this.ScriptTypes[number]=code;
    }

    @Override
    public String toString() {
        return "mapList{" +
                "TaskForces=" + Arrays.toString(TaskForces) +
                ", Tags=" + Tags +
                ", Triggers=" + Triggers +
                ", TeamTypes=" + Arrays.toString(TeamTypes) +
                ", ScriptTypes=" + Arrays.toString(ScriptTypes) +
                ", AITriggerTypes=" + AITriggerTypes +
                ", Events=" + Events +
                ", Actions=" + Actions +
                ", TaskForcesName=" + TaskForcesName +
                ", TagsName=" + TagsName +
                ", TriggersName=" + TriggersName +
                ", TeamTypesName=" + TeamTypesName +
                ", ScriptTypesName=" + ScriptTypesName +
                ", AITriggerTypesName=" + AITriggerTypesName +
                ", TagsTriggers=" + TagsTriggers +
                ", Terrains=" + Terrains +
                ", Waypoint=" + Waypoint +
                ", Countries=" + Arrays.toString(Countries) +
                ", maxnormalcode='" + maxnormalcode + '\'' +
                ", row_TaskLineList=" + row_TaskLineList +
                ", row_ScriptLineList=" + row_ScriptLineList +
                ", row_TagLineList=" + row_TagLineList +
                ", row_TriggerLineList=" + row_TriggerLineList +
                ", row_AITriggerLineList=" + row_AITriggerLineList +
                ", row_EventLineList=" + row_EventLineList +
                ", row_ActionLineList=" + row_ActionLineList +
                ", row_TeamTypeLineList=" + row_TeamTypeLineList +
                ", maxtaskForce=" + maxtaskForce +
                ", maxTeamType=" + maxTeamType +
                ", maxScriptType=" + maxScriptType +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
