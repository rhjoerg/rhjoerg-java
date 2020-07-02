package ch.rhjoerg.commons.reflect;

@FunctionalInterface
public interface ClassVisitor
{
	boolean visitClass(Class<?> type);

	default boolean enterClass(Class<?> type)
	{
		return true;
	}

	default boolean leaveClass(Class<?> type)
	{
		return true;
	}
}
