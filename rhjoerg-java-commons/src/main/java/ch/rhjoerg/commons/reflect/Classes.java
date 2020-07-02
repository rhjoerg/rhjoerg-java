package ch.rhjoerg.commons.reflect;

public interface Classes
{
	public static void walkClassTree(Class<?> type, ClassVisitor visitor)
	{
		if (type == null)
		{
			return;
		}

		if (visitor.enterClass(type))
		{
			if (visitor.visitClass(type))
			{
				for (Class<?> iface : type.getInterfaces())
				{
					walkClassTree(iface, visitor);
				}

				walkClassTree(type.getSuperclass(), visitor);
			}

			if (!visitor.leaveClass(type))
			{
				return;
			}
		}
	}
}
