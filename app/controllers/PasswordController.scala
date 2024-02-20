package controllers


import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import scala.collection.mutable
import models.{Password, NewPassword}
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Singleton
class PasswordController @Inject()(val controllerComponents: ControllerComponents)
extends BaseController {

private var pass = new mutable.ListBuffer[Password]()
  pass += Password(1, "ГосУслуги", "Gos_Uslugi_11", "null", LocalDate.of(2024,2,20), null)
  pass += Password(2, "МоиДокументы", "My_Documents_12", "null", LocalDate.of(2024,1,1), null)

  implicit val passwordJson = Json.format[Password]
  implicit val newPassword = Json.format[NewPassword]

    def getAllPasswords(): Action[AnyContent] = Action { 
        var currentDate: LocalDate = LocalDate.now()
        println(currentDate)
        if (pass.isEmpty) {
            NoContent
        } else {
            val html = views.html.passwords(pass.toList)
            // Ok(Json.toJson(todoList))
            Ok(html)
        }
    }


def addNewPassword() = Action { implicit request =>
   val content = request.body
    var jsonObject = content.asJson
      val passItem: Option[NewPassword] =
      jsonObject.flatMap(Json.fromJson[NewPassword](_).asOpt)

      passItem match{
        case Some(newPass) =>

        var nextId: Long = 0;
        if (pass.isEmpty) {
            nextId = 1;
        } else {
            nextId = pass.map(_.id).max + 1
        }
            val toBeAdded = Password(nextId, newPass.username, newPass.password, newPass.comment, LocalDate.now(), null)
            pass += toBeAdded
            Created(Json.toJson(toBeAdded))
        case None => 
            BadRequest
      }

  }


def addNewPasswordJSON() = Action(parse.json) { implicit request =>
    val newPasswordResult = request.body.validate[NewPassword]

    newPasswordResult.fold(
        errors => {
            BadRequest(Json.obj("message" -> JsError.toJson(errors)))
            Redirect(routes.PasswordController.getAllPasswords)
        },
        newPass => {
            val nextId = pass.map(_.id).max + 1
            val toBeAdded = Password(nextId, newPass.username, newPass.password, newPass.comment, LocalDate.now(), null)
            pass += toBeAdded
            Created(Json.toJson(toBeAdded))
        }
    )
}

def tes(id: Long, username: String) = Action {
            Ok(s"Id: $id, name: $username")
}

def passwordUsername(username: String) = Action {
            Ok(s" username: $username")
}

def login = Action{
    Ok(views.html.passwords(pass.toList))
}

def addPassword = Action { request =>
    val postVals = request.body.asFormUrlEncoded
    postVals.map { args => 
        val username = args("username").head
        val password = args("password").head
        val comment = args("comment").head

        var nextId: Long = 0;
        if (pass.isEmpty) {
            nextId = 1;
        } else {
            nextId = pass.map(_.id).max + 1
        }
        val toBeAdded = Password(nextId, username, password, comment, LocalDate.now(), null)
        pass += toBeAdded

        Redirect(routes.PasswordController.login)
         }.getOrElse(Ok("Oooops"))
}


    def validateLoginDelete = Action { request =>
    val postVals = request.body.asFormUrlEncoded
    postVals.map { args => 
        val id = args("id").head.toLong
println(id)
        pass = pass.filterNot(_.id == id)

        Redirect(routes.PasswordController.login)
         }.getOrElse(Ok("Oooops"))
    
}

  def deleteAllPasswords() = Action {
    pass.dropWhileInPlace(_.id  != 0)
    Accepted
  }

  def updatePassword(id: Long) = Action(parse.json[NewPassword]) { request =>
    val passwordRequest = request.body

    val updatedPassword = Password(id, passwordRequest.username, passwordRequest.password, passwordRequest.comment, LocalDate.now(), null)

    pass.find(_.id == id).map { existingPassword =>
        pass -= existingPassword
        pass += updatedPassword 
    }
   Ok(Json.obj("message" -> "Password update"))
  }

}