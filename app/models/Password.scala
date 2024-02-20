package models
import java.time.LocalDate

case class Password(id: Long, username: String, password: String, comment: String, created: LocalDate, deleted: LocalDate)
case class NewPassword(username: String, password: String, comment: String)