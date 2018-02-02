package controller

import io.javalin.Javalin
import dao.UserDao
import io.javalin.Context
import io.javalin.Handler
import kotlinx.coroutines.experimental.runBlocking
import model.CreateUserBody


class UserController {

    private val userDao: UserDao = UserDao()

    fun getUsers(): Handler {
        return Handler({ ctx ->

            runBlocking {

                val users = userDao.find(
                        ctx.queryParam("page")?.toIntOrNull(),
                        ctx.queryParam("size")?.toIntOrNull()
                )

                ctx.json(users)

            }

        })
    }


    fun getUserById(): Handler {
        return Handler({ ctx ->

            runBlocking {

                val user = userDao.findById(ctx.param("id")!!)

                user?.let { ctx.json(it) }
                    ?: ctx.status(404)
            }

        })
    }

    fun createUser(): Handler {
        return Handler({ ctx ->

            runBlocking {

                val user = ctx.bodyAsClass(CreateUserBody::class.java)
                userDao.save(name = user.name, email = user.email)
                ctx.status(201)

            }

        })
    }

    fun updateUser(): Handler {
        return Handler({ ctx ->

            runBlocking {

                val user = ctx.bodyAsClass(CreateUserBody::class.java)
                userDao.save(
                        _id = ctx.param("id")!!,
                        name = user.name,
                        email = user.email
                )
                ctx.status(204)
            }

        })
    }

    fun deleteUser(): Handler {
        return Handler({ ctx ->
            runBlocking {

                userDao.delete(ctx.param("id")!!)
                ctx.status(204)

            }

        })
    }


}
