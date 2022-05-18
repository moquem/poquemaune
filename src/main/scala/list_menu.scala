import sfml.window.*
import sfml.system.*
import sfml.graphics.*

class ListMenu(buttonList: Array[Button]) extends Menu {
 
  val font = Font("src/main/resources/fonts/Castforce.ttf")

  var currentPage = 0
  val maxPage = buttonList.size / 15 
  
  var shownButtons = buttonList.slice(0, 15)
  
  val nextPageButton = new Button(ButtonTextures.GenericMenu, (1280-100, 0), (100, 100))
  nextPageButton.setText("Next page", 60, font)
  nextPageButton.setActive(true)
  nextPageButton.setVisible(true)
  def nextPageOnClick() = {
    println("next page")
    currentPage += 1
    if (currentPage >= maxPage) {
      nextPageButton.setActive(false)
    }
    shownButtons.foreach(_.setVisible(false))
    println(currentPage)
    shownButtons = buttonList.slice(15 * currentPage, (15 * (currentPage + 1) - 1).min(buttonList.size - 1))
    updateButtonsShown()
  }
  nextPageButton.setOnClick(nextPageOnClick)


  val prevPageButton = new Button(ButtonTextures.GenericMenu, (0, 0), (100, 100))
  prevPageButton.setText("Previous page", 60, font)
  prevPageButton.setActive(true)
  prevPageButton.setVisible(true)
  def prevPageOnClick() = {
    currentPage -= 1
    if (currentPage <= 0) {
      prevPageButton.setActive(false)
    }
    shownButtons.foreach(_.setVisible(false))
    shownButtons = buttonList.slice(15 * currentPage, (15 * (currentPage + 1) - 1).min(buttonList.size - 1))
    updateButtonsShown()
  }
  prevPageButton.setOnClick(prevPageOnClick)
  
  def updateButtonsShown() = {
    for (i <- 0 to shownButtons.size - 1) {
      shownButtons(i).rect = IntRect(200 + ((1280 - 600)/2) * (i % 3), 50 + 135 * (i / 3), 200, 80)
      shownButtons(i).setActive(true)
      shownButtons(i).updateText()
    }
  }
  
  updateButtonsShown()
  shownButtons.map(_.setVisible(false))
  println("buttons should be invisible")
  def toGraphicObj(button: Button): GraphicObj = {return button}

  val buttons = Array[GraphicObj](nextPageButton, prevPageButton) ++ shownButtons.map(toGraphicObj(_))
  
  def getGraphicObjects(): Array[GraphicObj] = {
    return Array[GraphicObj]() ++ buttons
  }
}
