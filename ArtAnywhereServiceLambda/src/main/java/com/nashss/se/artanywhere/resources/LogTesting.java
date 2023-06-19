package com.nashss.se.artanywhere.resources;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class LogTesting {
    public static void main(final String[] args)
    {
        Logger logger = LogManager.getLogger();
        logger.debug("Debug Message Logged !!!");
        logger.info("Info Message Logged !!!");
        logger.error("Error Message Logged !!!", new NullPointerException("NullError"));
    }
}