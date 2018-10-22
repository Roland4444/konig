package abstractions

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, ObjectInputStream, ObjectOutputStream}

case class Message(message: Array[Byte], port: Int)  {
  def restoreMessage(input: Array[Byte]): Message = {
    val bis = new ByteArrayInputStream(input)
    var in = new ObjectInputStream(bis)
    var o =  in.readObject
    o.asInstanceOf[Message]
  }

  def saveMessagetoByte(event: Message): Array[Byte] = {
    val bos = new ByteArrayOutputStream
    var out = new ObjectOutputStream(bos)
    out.writeObject(event)
    out.flush()
    var Result = bos.toByteArray
    bos.close()
    Result
  }

}
