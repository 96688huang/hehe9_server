package cn.hehe9.common.exceptions;

/**
 * Error in application.conf
 */
public class ConfigurationException extends RuntimeException {

    /**
	 *
	 */
	private static final long serialVersionUID = -8117874744784339358L;

	public ConfigurationException(String message) {
        super(message);
    }

    public String getErrorDescription() {
        return getMessage();
    }

    public String getErrorTitle() {
        return "Configuration error.";
    }

}
