@rem Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
@rem ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.

@echo off

setlocal

if ["%JAVA_HOME%"]==[""] (
    echo "JAVA_HOME is not set"
    exit /b 1
)

if ["%EJDK_HOME%"]==[""] (
    for %%A in ("%0") do set EJDK_HOME=%%~dpA..\
)

"%JAVA_HOME%\bin\java" -Xms512m -Xmx512m -Xbootclasspath/p:%EJDK_HOME%\lib\JRECreate.jar -Dejdk.home=%EJDK_HOME% -jar %EJDK_HOME%\lib\JRECreate.jar  %*

exit /b %ERRORLEVEL%
