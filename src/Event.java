final class Event
{
   public ActionInterface action;
   public long time;
   public WorldEntity entity;

   public Event(ActionInterface action, long time, WorldEntity entity)
   {
      this.action = action;
      this.time = time;
      this.entity = entity;
   }
}
