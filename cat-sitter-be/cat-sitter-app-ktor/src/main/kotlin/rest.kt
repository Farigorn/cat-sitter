import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.booking(appSetting: MkplAppSetting) {
    route("booking") {
        post("/create") {
            call.   createBooking(appSetting)
        }
        post("/read") {
            call.readBooking(appSetting)
        }
        post("/update") {
            call.updateBooking(appSetting)
        }
        post("/delete") {
            call.deleteBooking(appSetting)
        }
    }
}