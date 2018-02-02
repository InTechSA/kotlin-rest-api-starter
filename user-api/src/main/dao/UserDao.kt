package dao

import com.mongodb.async.client.MongoCollection
import model.User
import org.litote.kmongo.coroutine.*
import org.litote.kmongo.async.KMongo

class UserDao {

    private val collection: MongoCollection<User>

    init {
      println("init db connection")

      val client = KMongo.createClient()
      val database = client.getDatabase("test")
      this.collection = database.getCollection()

      println("connected !!")
    }

    suspend fun save(name: String, email: String, _id: String? = null) {
        collection.save(User(name = name, email = email, _id = _id))
    }

    suspend fun findById(_id: String):User? {
       return collection.findOneById(_id)
    }

    suspend fun find(page: Int?, size: Int?):MutableList<User> {
        return collection.find().skip((page?:0) * (size?:10)).limit(size?:10).toList()
    }

    suspend fun findByEmail(email: String):User? {
        return collection.findOne("{email: '$email'}")
    }

    suspend fun delete(_id: String) {
        collection.deleteOneById(_id)
    }

}