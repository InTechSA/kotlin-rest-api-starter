package dao

import com.mongodb.async.client.MongoCollection
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import model.User
import org.litote.kmongo.coroutine.* //NEEDED! import KMongo extensions
import org.litote.kmongo.async.KMongo

class UserDao {

    val collection: MongoCollection<User>

    init {
      println("init db connection")

      val client = KMongo.createClient()
      val database = client.getDatabase("test")
      this.collection = database.getCollection<User>()

      println("connected !!")
    }

    fun save(name: String, email: String, _id: String? = null) {
        launch(CommonPool) {
            collection.save(User(name = name, email = email, _id = _id))
        }
    }

    fun findById(_id: String):Deferred<User?> {
        return async(CommonPool){
            collection.findOneById(_id)
        }
    }

    fun find(page: Int?, size: Int?):Deferred<MutableList<User>> {
        return async(CommonPool) {
            collection.find().skip((page?:0) * (size?:10)).limit(size?:10).toList()
        }

    }

    fun findByEmail(email: String):Deferred<User?> {
        return async(CommonPool){
            collection.findOne("{email: '$email'}")
        }
    }

    fun delete(_id: String) {
        launch(CommonPool){
            collection.deleteOneById(_id)
        }
    }

}