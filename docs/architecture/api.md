# API

1.Booking (Бронирование)
Назначение: Оформление заказа у выбранного ситтера (Cat Sitter). Владелец (Pet Owner) выбирает ситтера

Поля (пример):

id: Long — Идентификатор бронирования.
ownerId: Long — Владелец, который оформляет бронирование.
sitterId: Long — Ссылка на пользователя с ролью ROLE_SITTER.
startDate: LocalDate
endDate: LocalDate
notes: String — Дополнительные пожелания владельца (корм, лоток, поведение).
status: BookingStatus — (NEW, ACCEPTED, REJECTED, CANCELLED, COMPLETED и т.д.).

Методы / операции:

createBooking(Booking booking): Создать новую бронь (Pet Owner инициирует).
updateBooking(Booking booking):
Ситтер может «принять» (меняем статус на ACCEPTED) или «отклонить» (REJECTED).
deleteBooking(Long id): Удалить бронирование

2. SitterProfile (Профиль ситтера)
   Назначение: Хранит информацию, специфичную для ситтера. Может создаваться:
   Cразу, если пользователь регистрируется в роли Cat Sitter.
   Поля (пример):

id: Long — Идентификатор профиля.
userId: Long — Ссылка на User (который теперь имеет ROLE_SITTER).
rate: BigDecimal или Double — Стоимость услуг или диапазон цен.
experience: String — Более подробное описание опыта.
availability: String — Информация о доступных датах/графике.
rating: Float — Средний рейтинг (может рассчитываться по отзывам).
