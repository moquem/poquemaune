import java.awt.Color
import javax.swing.ImageIcon
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing._
import javax.swing.JFrame
import javax.swing.JScrollPane
import javax.swing.JLabel
import javax.swing.JTextArea
import java.awt.image.BufferedImage
import java.awt.{Graphics2D,Color,Font,BasicStroke}
import javax.imageio.ImageIO
import java.io.InputStream
import java.io.File
import java.awt.GridLayout
import java.awt.GridBagLayout
import java.awt.LayoutManager
import java.awt._
import javax.swing.border.Border
import javax.swing.BorderFactory
import java.awt.GraphicsDevice
import java.awt.GraphicsEnvironment
import java.awt.event._
import layout.TableLayout


class pokemonDescription (pok:Pokemon) extends JPanel{
  this.setVisible(true)
  val columns = Array(0.33, 0.34, 0.33)
  val rows = Array(0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1)
  this.setLayout(new TableLayout(Array(columns, rows)))

  val pokNameLabel = new JLabel("Name : " + pok.pokemonName)
  val atkStatLabel = new JLabel("Attack : " + pok.statAtt.toString)
  val defStatLabel = new JLabel("Defence : " + pok.statDef.toString)
  val pokTypeLabel = new JLabel("Type : " + pok.typ)

  this.add(pokNameLabel, "1, 0, 1, 0")
  this.add(pokTypeLabel, "1, 1, 1, 1")
  this.add(atkStatLabel, "1, 2, 1, 2")
  this.add(defStatLabel, "1, 3, 1, 3")

  val pokImage = new ImageIcon(pok.lien)
  val pokImgButton = new JButton(pokImage)
  pokImgButton.setVisible(true)
  pokImgButton.setEnabled(true)
  pokImgButton.setBorderPainted(false)
  this.add(pokImgButton, "2, 0, 2, 9")

  pok.set_attack.zipWithIndex.foreach{case (atk, index) => this.add(new JLabel("Attack " + (index+1).toString + " : " + atk.attackName), ("1, " + (index+4).toString + ", 1, " + (index+4).toString))}

}
