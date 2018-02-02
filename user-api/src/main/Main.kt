import controller.UserController
import io.javalin.ApiBuilder.*
import io.javalin.Javalin

fun main(args: Array<String>) {

    val p =  System.getenv("PORT")?.toIntOrNull()?:7000


    val app = Javalin.create().apply {
        enableStandardRequestLogging()
        exception(Exception::class.java) { e, _ -> e.printStackTrace() }
        error(404) { ctx -> ctx.json("not found") }
        port(p)
    }.start()

    val userController = UserController()

    app.routes {
        path("users") {

            get(userController::getUsers.call())

            post(userController::createUser.call())

            path(":id") {

                get(userController::getUserById.call())

                patch(userController::updateUser.call())

                delete(userController::deleteUser.call())

            }

        }
    }
}