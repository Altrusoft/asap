name := "Altrusoft Alfresco Play Integration (ASAP)"

version := "1.0-SNAPSHOT"

resolvers += "Alfresco OSS Releases" at "https://artifacts.alfresco.com/nexus/content/groups/public"

libraryDependencies ++= Seq(
    "org.alfresco" % "alfresco-core" % "4.2.e",
    "org.alfresco" % "alfresco-data-model" % "4.2.e",
    "org.alfresco" % "alfresco-repository" % "4.2.e"
)
