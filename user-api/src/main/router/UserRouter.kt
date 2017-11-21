package router

import io.javalin.ApiBuilder.get
import io.javalin.ApiBuilder.post
import io.javalin.ApiBuilder.patch
import io.javalin.ApiBuilder.delete
import io.javalin.Javalin
import dao.UserDao
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.runBlocking
import model.CreateUserBody

fun main(args: Array<String>) {

    val userDao = UserDao()

    val app = Javalin.create().apply {
        port(7000)
        exception(Exception::class.java) { e, _ -> e.printStackTrace() }
        error(404) { ctx -> ctx.json("not found") }
    }.start()

    app.routes {

        get("/users") {
            ctx ->

            runBlocking(CommonPool) {

                val users = userDao.find(
                    ctx.queryParam("page")?.toIntOrNull(),
                    ctx.queryParam("size")?.toIntOrNull()
                ).await()

                ctx.json(users)
            }


        }

        get("/users/:id") { ctx ->
            runBlocking {
                val user = userDao.findById(ctx.param("id")!!).await()

                user?.let {
                    ctx.json(it)
                } ?: run {
                    ctx.status(404)
                }

            }

        }

        get("/users/email/:email") { ctx ->
            runBlocking {
                val user = userDao.findByEmail(ctx.param("email")!!).await()

                user?.let {
                    ctx.json(it)
                } ?: run {
                    ctx.status(404)
                }
            }
        }

        post("/users/create") { ctx ->
            val user = ctx.bodyAsClass(CreateUserBody::class.java)
            userDao.save(name = user.name, email = user.email)
            ctx.status(201)
        }

        patch("/users/update/:id") { ctx ->
            val user = ctx.bodyAsClass(CreateUserBody::class.java)
            userDao.save(
                    _id = ctx.param("id")!!,
                    name = user.name,
                    email = user.email
            )
            ctx.status(204)
        }

        delete("/users/delete/:id") { ctx ->
            userDao.delete(ctx.param("id")!!)
            ctx.status(204)
        }

    }

}