package user

import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec
import model.User
import utils.Constants

class UserTest : StringSpec() {
    init {
        "User.isAdmin should return true if the user is an admin" {
            User(name = "Unit Tester", email = "unit@tester.com", role = Constants.ADMIN_ROLE, _id = "a random id").isAdmin() shouldBe true
        }
    }
}