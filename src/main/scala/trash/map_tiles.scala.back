import javax.swing.ImageIcon
import java.awt.BorderLayout
import javax.swing._
import java.io.InputStream
import java.io.File
import java.awt.GridLayout
import java.awt.GridBagLayout
import java.awt.LayoutManager
import java.awt._
import javax.swing.border.Border
import javax.swing.BorderFactory
import java.awt.event._
import layout.TableLayout



trait Tile extends JButton {
  def updateTile(new_img:ImageIcon) : Unit
  this.setBorderPainted(false)
}

class MapTile (image:ImageIcon) extends Tile {
  this.setVisible(true)
 def updateTile (new_img:ImageIcon): Unit = {
    if (new_img != this.getIcon()){
      this.setIcon(new_img)
    }
  }
}

// Tile representing the player
class PlayerTile () extends Tile {
  this.setVisible(true)
  def updateTile (new_img:ImageIcon): Unit = {
    this.setIcon(new_img)
  }
}


class SelectionPanel (layout:Array[Array[Double]]) extends JPanel {
  this.setVisible(true)
  
  this.setLayout(new TableLayout (layout))

}


sealed trait Direction
  case object Right extends Direction
  case object Left extends Direction
  case object Up extends Direction
  case object Down extends Direction

class Map (self:JPanel, size_x:Int, size_y:Int, mapImages:Array[ImageIcon], mapLayout:Array[Array[Int]], backImage:ImageIcon, playerImage:ImageIcon, playerTileCoord:(Int, Int)) extends JPanel {
  
  var curr_x_pos = 0
  var curr_y_pos = 0
  
  var mapImagesResized = mapImages
  var playerImageResized = playerImage

  var windowResized = true

  var tiles = Array.fill[Array[Tile]](size_x)(Array.fill[Tile](size_y)(new MapTile(backImage)))
 
  // this means that the player will start at position 0, 0, should probably be passed as parameter
  tiles(0)(0) = new PlayerTile()

  // checks if the given coordinates are within the bounds of the map
  def in_bounds (x_pos:Int, y_pos:Int) : Boolean = {
    return !(x_pos >= size_x || x_pos < 0) && !(y_pos>=size_y || y_pos < 0)
  }
  
  // resizes an image to the given size
  def resizeImage (imgIcon:ImageIcon, new_x:Int, new_y:Int) : ImageIcon = {
    val imgToResize = imgIcon.getImage()
    val resizedImg = imgToResize.getScaledInstance(new_x, new_y, Image.SCALE_SMOOTH)
    return new ImageIcon(resizedImg)
  }

  // adds all the tiles to the panel
  def init (panel:JPanel): Unit = {
    
    val map_columns = Array.fill[Double](size_y)((1).toDouble/size_y)
    val map_rows = Array.fill[Double](size_x)((1.toDouble)/size_x)
    val map_table = Array(map_rows, map_columns)
    panel.setLayout(new TableLayout(map_table))
    panel.setVisible(true)
    
    for (i<-0 until size_x){
      for (j<-0 until size_y){
        tiles(i)(j) = new MapTile(mapImages(mapLayout(i)(j)))
        val tile = tiles(i)(j)
        this.add(tile, i.toString + ", " + j.toString + ", " + i.toString + ", " + j.toString)
        tile.setVisible(true)
        //tile.setBorderPainted(false)
      }
    }

    // sets up the player
    tiles(playerTileCoord._1)(playerTileCoord._2) = new PlayerTile()
    val playerTile = tiles(playerTileCoord._1)(playerTileCoord._2)
    this.add(playerTile, playerTileCoord._1.toString + ", " + playerTileCoord._2.toString + ", " + playerTileCoord._1.toString + ", " + playerTileCoord._2.toString)
  }

  /*def update_image_size (mapImages:Array[ImageIcon]) : Array[ImageIcon] = {
    var resizedMapImages = mapImages
    if (windowResized) {
      // assumes all the tiles are the same size (if it isn't the case, there is an issue)
      val ex_tile = tiles(0)(0)
      // resizes player
      tiles(playerTileCoord._1)(playerTileCoord._2).setIcon(resizeImage(playerImage, ex_tile.getWidth(), ex_tile.getHeight()))
      for (k<-0 until mapImages.size){
        resizedMapImages(k) = resizeImage(mapImages(k), ex_tile.getWidth(), ex_tile.getHeight())
      }
      windowResized = false
      return resizedMapImages
    }
    else {
      return resizedMapImages
    }
  }*/

  def resize_sprite_images (): Unit = {
    var resizedMapImages = mapImages
    // assumes all the tiles are the same size (if it isn't the case, there is an issue)
    val ex_tile = tiles(0)(0)
    // resizes player
    playerImageResized = resizeImage(playerImage, ex_tile.getWidth(), ex_tile.getHeight())
    for (k<-0 until mapImages.size){
      resizedMapImages(k) = resizeImage(mapImages(k), ex_tile.getWidth(), ex_tile.getHeight())
    }

  }

  // moves the map
  def move_player_dir (dir:Direction): Unit = {
    // Resizes the images if the window has chaged size since the last time this function was called
    
    var new_pos_x = 0
    var new_pos_y = 0
    dir match{
      case Right =>
        new_pos_x = curr_x_pos+1;
        new_pos_y = curr_y_pos;
      case Left =>
        new_pos_x = curr_x_pos-1;
        new_pos_y = curr_y_pos;
      case Up =>
        new_pos_x = curr_x_pos;
        new_pos_y = curr_y_pos-1;
      case Down =>
        new_pos_x = curr_x_pos;
        new_pos_y = curr_y_pos+1;
    }
    
    val i = 0
    val j = 0
    for (i<-0 until size_x){
      for (j<-0 until size_y){
        val tile = tiles(i)(j)
        if (in_bounds(new_pos_x+i, new_pos_y+j)){
          val newTileImage = mapImagesResized(mapLayout(new_pos_x+i)(new_pos_y+j))
          tile.updateTile(newTileImage)
        }
        else {
          tile.updateTile(backImage)
        }
      }
    }
    tiles(playerTileCoord._1)(playerTileCoord._2).updateTile(playerImageResized)

    curr_x_pos = new_pos_x
    curr_y_pos = new_pos_y
  }
}


