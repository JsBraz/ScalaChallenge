package challenge

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class OrderAnalyzer(orders: List[Order]) {

  /*
  This method takes a start date and an end date and returns a list of orders that were placed between those two dates.
   It does this by filtering the input list of orders using the filter method and a comparison of the order's date to the start and end dates.
   */
  def filterOrdersByDateRange(startDate: LocalDate, endDate: LocalDate): List[Order] = {
    orders.filter(o => o.date.compareTo(startDate) >= 0 && o.date.compareTo(endDate) <= 0)
  }

  /**
   * Groups the orders based on the age of the oldest product in each order.
   * For each age interval specified in the given list of tuples, filters the orders based on the age
   * of the oldest product in months, and returns a list of tuples where the first element is the age
   * interval label and the second element is the count of orders in that interval.
   *
   * @param intervals a list of tuples representing the age intervals, where each tuple consists of
   *                  the start and end age in months (inclusive)
   * @param startDate the start date to calculate the age of the oldest product from
   * @return a list of tuples representing the age intervals and the count of orders in each interval
   */

  def groupOrdersByProductAge(intervals: List[(Int, Int)], startDate: LocalDate): List[(String, Int)] = {
    // Map each age interval to a tuple of the interval label and the count of orders in that interval
    val groups = intervals.map(interval => {
      val (start, end) = interval
      // Filter orders based on the age of the oldest product in the order
      val groupOrders = orders.filter(o => {
        // Get the oldest product's creation date for the order
        val oldestProduct = o.items.minBy(_.product.creationDate)
        // Calculate the age of the oldest product in months from the given start date
        val productCreationDate = oldestProduct.product.creationDate
        val productAgeInMonths = productCreationDate.until(o.date).toTotalMonths.toInt
        productAgeInMonths >= start && productAgeInMonths <= end
      })
      // Create a label for the age interval based on the start and end values
      // If the end value is Int.MaxValue, represent it as ">start months"
      val groupLabel = if (start == 12 && end == Int.MaxValue) {
        s">$start months"
      } else {
        s"$start-${end} months"
      }
      // Create a tuple with the group label and the count of orders in the group
      (groupLabel, groupOrders.size)
    })
    groups
  }
}

object OrderAnalyzerApp {
  def main(args: Array[String]): Unit = {
    val orders = List(

      Order("Customer A", "contact1@example.com", "Rua 123, Portugal", 50.0, LocalDate.parse("2022-01-01", DateTimeFormatter.ISO_LOCAL_DATE), List(
        Item(Product("Product A", "Category 1", 1.0, 10.0, LocalDate.parse("2021-12-01", DateTimeFormatter.ISO_LOCAL_DATE)), 10.0, 2.0, 1.0),
        Item(Product("Product B", "Category 2", 2.0, 20.0, LocalDate.parse("2021-11-01", DateTimeFormatter.ISO_LOCAL_DATE)), 20.0, 3.0, 2.0),
        //Item(Product("Product B", "Category 2", 2.0, 20.0, LocalDate.parse("2020-12-01", DateTimeFormatter.ISO_LOCAL_DATE)), 20.0, 3.0, 2.0)

      )),
      Order("Customer B", "contact2@example.com", "Rua 456 Portugal", 100.0, LocalDate.parse("2021-02-01", DateTimeFormatter.ISO_LOCAL_DATE), List(
        Item(Product("Product C", "Category 1", 1.5, 15.0, LocalDate.parse("2021-10-01", DateTimeFormatter.ISO_LOCAL_DATE)), 15.0, 3.0, 1.5),
        Item(Product("Product D", "Category 3", 3.0, 30.0, LocalDate.parse("2021-08-01", DateTimeFormatter.ISO_LOCAL_DATE)), 30.0, 4.0, 3.0)
      )),
      Order("Customer C", "contact3@example.com", "Rua 789, Portugal", 150.0, LocalDate.parse("2022-04-01", DateTimeFormatter.ISO_LOCAL_DATE), List(
        Item(Product("Product E", "Category 2", 2.5, 25.0, LocalDate.parse("2021-03-01", DateTimeFormatter.ISO_LOCAL_DATE)), 25.0, 5.0, 2.5),
        Item(Product("Product F", "Category 1", 1.5, 15.0, LocalDate.parse("2021-12-01", DateTimeFormatter.ISO_LOCAL_DATE)), 15.0, 3.0, 1.5),
        Item(Product("Product G", "Category 3", 4.0, 40.0, LocalDate.parse("2021-09-01", DateTimeFormatter.ISO_LOCAL_DATE)), 40.0, 6.0, 4.0)
      )),
      Order("Customer D", "contact4@example.com", "Rua 172, Portugal", 200.0, LocalDate.parse("2021-06-01", DateTimeFormatter.ISO_LOCAL_DATE), List(
        Item(Product("Product H", "Category 2", 3.0, 30.0, LocalDate.parse("2021-02-01", DateTimeFormatter.ISO_LOCAL_DATE)), 30.0, 4.0, 3.0),
        Item(Product("Product I", "Category 1", 2.0, 20.0, LocalDate.parse("2021-06-01", DateTimeFormatter.ISO_LOCAL_DATE)), 20.0, 3.0, 2.0)
      )),

      Order("Customer E", "contact5@example.com", "Rua 895, Portugal", 200.0, LocalDate.parse("2022-08-01", DateTimeFormatter.ISO_LOCAL_DATE), List(
        Item(Product("Product L", "Category 1", 2.0, 20.0, LocalDate.parse("2021-11-01", DateTimeFormatter.ISO_LOCAL_DATE)), 20.0, 3.0, 2.0),
        Item(Product("Product L", "Category 1", 2.0, 20.0, LocalDate.parse("2021-08-01", DateTimeFormatter.ISO_LOCAL_DATE)), 20.0, 3.0, 2.0)
      )),

      Order("Customer F", "contact6@example.com", "Rua 895, Portugal", 200.0, LocalDate.parse("2022-02-01", DateTimeFormatter.ISO_LOCAL_DATE), List(
        Item(Product("Product L", "Category 1", 2.0, 20.0, LocalDate.parse("2021-01-01", DateTimeFormatter.ISO_LOCAL_DATE)), 20.0, 3.0, 2.0)
      ))

    )
    val analyzer = new OrderAnalyzer(orders)

    // Filter orders by date range
    val startDate = LocalDate.parse(args(0), DateTimeFormatter.ISO_LOCAL_DATE)
    val endDate = LocalDate.parse(args(1), DateTimeFormatter.ISO_LOCAL_DATE)
    val filteredOrders = analyzer.filterOrdersByDateRange(startDate, endDate)

    // Group orders by product age intervals
    val intervals = List(
      (0, 3),
      (3, 6),
      (6, 9),
      (9, 12),
      (12, Int.MaxValue)
    )
    val groupedOrders = analyzer.groupOrdersByProductAge(intervals, startDate)

    // Print results
    println(s"Filtered orders between $startDate and $endDate:")
    filteredOrders.foreach(println)

    println("\nGrouped orders by product age:")
    groupedOrders.foreach(println)
  }
}



