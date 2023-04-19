import com.github.polomarcus.utils.ClimateService
import com.github.polomarcus.model.CO2Record
import org.scalatest.funsuite.AnyFunSuite

//@See https://www.scalatest.org/scaladoc/3.1.2/org/scalatest/funsuite/AnyFunSuite.html
class ClimateServiceTest extends AnyFunSuite {
  test("isClimateRelated - non climate related words should return false") {
    assert( !ClimateService.isClimateRelated("pizza"))
  }

  test("isClimateRelated - climate related words should return true") {
    assert(ClimateService.isClimateRelated("climate change"))
    assert(ClimateService.isClimateRelated("IPCC") )
  }

  test("isClimateRelated - climate related words should return true regardless of letter case") {
    assert(ClimateService.isClimateRelated("cLiMaTe change"))
  }

  //@TODO
  test("parseRawData") {
    // our inputs
    val firstRecord = (2003, 1, 355.2)     //help: to access 2003 of this tuple, you can do firstRecord._1
    val secondRecord = (2004, 1, 375.2)
    val list1 = List(firstRecord, secondRecord)

    // our output of our method "parseRawData"
    val co2RecordWithType = CO2Record(firstRecord._1, firstRecord._2, firstRecord._3)
    val co2RecordWithType2 = CO2Record(secondRecord._1, secondRecord._2, secondRecord._3)
    val output = List(Some(co2RecordWithType), Some(co2RecordWithType2))

    // we call our function here to test our input and output
    assert(ClimateService.parseRawData(list1) == output)

  }

  test("parseRawData - shouldn't return value of ppm inferior or equal to zero") {
    val first_record= (2010, 1, -200.2)
    val second_record = (2001,3, 0.1)
    val list = List(first_record, second_record)

    val co2RecordWithType2 = CO2Record(second_record._1, second_record._2, second_record._3)
    val output = List(None, Some(co2RecordWithType2))
    assert(ClimateService.parseRawData(list) == output)
  }
  //@TODO
  test("filterDecemberData") {
    val first_record = (2010, 1, 200.2)
    val second_record = (2001,12,5.2)
    val list = List(first_record, second_record)
    val input = ClimateService.parseRawData(list)
    assert(ClimateService.filterDecemberData(input)==List(CO2Record(first_record._1,first_record._2,first_record._3)))
  }

  test("getMinMax"){
    val first_record = (2010, 1, 200.2)
    val second_record = (2001, 12, 5.2)
    val third_record = (2005,12,0.1)
    val fourth_record = (2005,3,5.0)
    val fifth_record = (2005,4,2.0)
    val list = List(first_record, second_record, third_record, fourth_record, fifth_record)
    val input = ClimateService.parseRawData(list).flatten
    assert(ClimateService.getMinMax(input)==(0.1,200.2))
  }

  test("getMinMaxByYear") {
    val first_record = (2010, 1, 200.2)
    val second_record = (2001, 12, 5.2)
    val third_record = (2005, 12, 0.1)
    val fourth_record = (2005, 3, 5.0)
    val fifth_record = (2005, 4, 2.0)
    val list = List(first_record, second_record, third_record, fourth_record, fifth_record)
    val input = ClimateService.parseRawData(list).flatten
    assert(ClimateService.getMinMaxByYear(input,2005) == (0.1, 5.0))
  }
}

