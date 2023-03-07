package challenge

import java.time.LocalDate

case class Order(customerName: String, contact: String, shippingAddress: String, grandTotal: Double, date: LocalDate, items: List[Item])
