rootProject.name = "workbench-tracing"

include("novelAiService")
include("authorService")

val upperCaseLetters = "\\p{Upper}".toRegex()

fun String.toKebabCase() =
        replace(upperCaseLetters) { "-${it.value.toLowerCase()}" }

fun buildFileNameFor(projectDirName: String) =
        "${projectDirName}.gradle.kts"

for (project in rootProject.children) {
    val projectDirName = project.name .toKebabCase()
    project.projectDir = file("$projectDirName")
    project.buildFileName = buildFileNameFor(projectDirName)
    require(project.projectDir.isDirectory) {
        "Project directory ${project.projectDir} for project ${project.name} does not exist."
    }
    require(project.buildFile.isFile) {
        "Build file ${project.buildFile} for project ${project.name} does not exist."
    }
}
