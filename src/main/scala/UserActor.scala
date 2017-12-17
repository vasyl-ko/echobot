import UserActor.{SendNow, UserMessage}
import akka.actor.{Actor, Cancellable, Props}
import akka.event.Logging
import info.mukel.telegrambot4s.models.Message

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class UserActor(userId: String) extends Actor {
  val log = Logging(context.system, this)

  var currentMessage: Option[UserMessage] = None
  var currentCancellable: Option[Cancellable] = None

  def receive = {
    case userMessage: UserMessage =>
      log.info(s"received new message: ${userMessage.value}")
      currentMessage = Option(userMessage)
      currentCancellable.map(_.cancel())
      currentCancellable = Option(context.system.scheduler.schedule(1.second, 1.second, self, SendNow))
    case SendNow =>
      currentMessage.foreach { message =>
        context.parent ! SendMessage(message)(message.message)
      }
    case _ =>
      log.info("received unknown message")
  }
}

object UserActor {

  case class UserMessage(value: String)(implicit val message: Message)

  case object SendNow

  def props(userId: String): Props = Props(new UserActor(userId))
}
