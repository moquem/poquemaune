import sfml.window.*
import sfml.system.*
import sfml.graphics.*

class ListMenu(buttonList: Array[Button]) extends Menu {
 
  val font = Font("src/main/resources/fonts/Castforce.ttf")

  var currentPage = 0
  val maxPage = buttonList.size / 15 
  buttonList.foreach(_.setVisible(false))


  var shownButtons = buttonList.slice(0, (15).min(buttonList.size))
  
  val nextPageButton = new Button(ButtonTextures.GenericMenu, (1280-100, 0), (100, 100))
  nextPageButton.setText("Next page", 60, font)
  nextPageButton.setActive(true)
  nextPageButton.setVisible(true)
  def nextPageOnClick() = {
    currentPage += 1
    updateNavigationButtons()
    shownButtons = buttonList.slice(15 * currentPage, (15 * (currentPage + 1)).min(buttonList.size))
    updateButtonsShown()
  }
  nextPageButton.setOnClick(nextPageOnClick)


  val prevPageButton = new Button(ButtonTextures.GenericMenu, (0, 0), (100, 100))
  prevPageButton.setText("Previous page", 60, font)
  prevPageButton.setActive(false)
  prevPageButton.setVisible(true)
  def prevPageOnClick() = {
    currentPage -= 1
    updateNavigationButtons()
    shownButtons = buttonList.slice(15 * currentPage, (15 * (currentPage + 1)).min(buttonList.size))
    updateButtonsShown()
  }
  prevPageButton.setOnClick(prevPageOnClick)
  
  def updateButtonsShown() = {
    buttonList.foreach(_.setVisible(false))
    for (i <- 0 to shownButtons.size - 1) {
      shownButtons(i).rect = IntRect(200 + ((1280 - 600)/2) * (i % 3), 50 + 135 * (i / 3), 200, 80)
      shownButtons(i).setActive(true)
      shownButtons(i).setVisible(true)
      shownButtons(i).updateText()
    }
  }

  def updateNavigationButtons() = {
    prevPageButton.setActive((currentPage != 0))
    nextPageButton.setActive((currentPage != maxPage))
  }
  
  updateButtonsShown()
  updateNavigationButtons()

  def toGraphicObj(button: Button): GraphicObj = {return button}

  val buttons = Array[GraphicObj](nextPageButton, prevPageButton) 
  
  def getGraphicObjects(): Array[GraphicObj] = {
    return buttons ++ shownButtons.map(toGraphicObj(_))
  }

  def getDisplayedObjects(): Array[GraphicObj] = {
    return getGraphicObjects()
  }
}
