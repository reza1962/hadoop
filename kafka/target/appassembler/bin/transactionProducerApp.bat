@REM ----------------------------------------------------------------------------
@REM  Copyright 2001-2006 The Apache Software Foundation.
@REM
@REM  Licensed under the Apache License, Version 2.0 (the "License");
@REM  you may not use this file except in compliance with the License.
@REM  You may obtain a copy of the License at
@REM
@REM       http://www.apache.org/licenses/LICENSE-2.0
@REM
@REM  Unless required by applicable law or agreed to in writing, software
@REM  distributed under the License is distributed on an "AS IS" BASIS,
@REM  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@REM  See the License for the specific language governing permissions and
@REM  limitations under the License.
@REM ----------------------------------------------------------------------------
@REM
@REM   Copyright (c) 2001-2006 The Apache Software Foundation.  All rights
@REM   reserved.

@echo off

set ERROR_CODE=0

:init
@REM Decide how to startup depending on the version of windows

@REM -- Win98ME
if NOT "%OS%"=="Windows_NT" goto Win9xArg

@REM set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" @setlocal

@REM -- 4NT shell
if "%eval[2+2]" == "4" goto 4NTArgs

@REM -- Regular WinNT shell
set CMD_LINE_ARGS=%*
goto WinNTGetScriptDir

@REM The 4NT Shell from jp software
:4NTArgs
set CMD_LINE_ARGS=%$
goto WinNTGetScriptDir

:Win9xArg
@REM Slurp the command line arguments.  This loop allows for an unlimited number
@REM of arguments (up to the command line limit, anyway).
set CMD_LINE_ARGS=
:Win9xApp
if %1a==a goto Win9xGetScriptDir
set CMD_LINE_ARGS=%CMD_LINE_ARGS% %1
shift
goto Win9xApp

:Win9xGetScriptDir
set SAVEDIR=%CD%
%0\
cd %0\..\.. 
set BASEDIR=%CD%
cd %SAVEDIR%
set SAVE_DIR=
goto repoSetup

:WinNTGetScriptDir
set BASEDIR=%~dp0\..

:repoSetup


if "%JAVACMD%"=="" set JAVACMD=java

if "%REPO%"=="" set REPO=%BASEDIR%\repo

set CLASSPATH="%BASEDIR%"\etc;"%REPO%"\spring-integration-kafka-1.2.1.RELEASE.jar;"%REPO%"\metrics-annotation-2.2.0.jar;"%REPO%"\reactor-core-2.0.3.RELEASE.jar;"%REPO%"\reactive-streams-1.0.0.jar;"%REPO%"\metrics-core-2.2.0.jar;"%REPO%"\kafka_2.10-0.8.2.1.jar;"%REPO%"\scala-library-2.10.4.jar;"%REPO%"\zookeeper-3.4.6.jar;"%REPO%"\jline-0.9.94.jar;"%REPO%"\jopt-simple-3.2.jar;"%REPO%"\zkclient-0.3.jar;"%REPO%"\gs-collections-5.0.0.jar;"%REPO%"\gs-collections-api-5.0.0.jar;"%REPO%"\avro-1.7.7.jar;"%REPO%"\jackson-core-asl-1.9.13.jar;"%REPO%"\jackson-mapper-asl-1.9.13.jar;"%REPO%"\paranamer-2.3.jar;"%REPO%"\snappy-java-1.0.5.jar;"%REPO%"\commons-compress-1.4.1.jar;"%REPO%"\xz-1.0.jar;"%REPO%"\avro-maven-plugin-1.7.7.jar;"%REPO%"\maven-plugin-api-2.0.10.jar;"%REPO%"\maven-project-2.0.10.jar;"%REPO%"\maven-settings-2.0.10.jar;"%REPO%"\maven-profile-2.0.10.jar;"%REPO%"\maven-model-2.0.10.jar;"%REPO%"\maven-artifact-manager-2.0.10.jar;"%REPO%"\maven-repository-metadata-2.0.10.jar;"%REPO%"\wagon-provider-api-1.0-beta-2.jar;"%REPO%"\maven-plugin-registry-2.0.10.jar;"%REPO%"\plexus-interpolation-1.1.jar;"%REPO%"\plexus-utils-1.5.5.jar;"%REPO%"\maven-artifact-2.0.10.jar;"%REPO%"\plexus-container-default-1.0-alpha-9-stable-1.jar;"%REPO%"\classworlds-1.1-alpha-2.jar;"%REPO%"\file-management-1.2.1.jar;"%REPO%"\maven-shared-io-1.1.jar;"%REPO%"\avro-compiler-1.7.7.jar;"%REPO%"\commons-lang-2.6.jar;"%REPO%"\velocity-1.7.jar;"%REPO%"\commons-collections-3.2.1.jar;"%REPO%"\avro-ipc-1.7.7.jar;"%REPO%"\jetty-6.1.26.jar;"%REPO%"\jetty-util-6.1.26.jar;"%REPO%"\netty-3.4.0.Final.jar;"%REPO%"\servlet-api-2.5-20081211.jar;"%REPO%"\spring-integration-stream-4.2.0.RELEASE.jar;"%REPO%"\spring-integration-core-4.1.1.RELEASE.jar;"%REPO%"\spring-messaging-4.1.3.RELEASE.jar;"%REPO%"\spring-beans-4.1.3.RELEASE.jar;"%REPO%"\spring-core-4.1.3.RELEASE.jar;"%REPO%"\commons-logging-1.2.jar;"%REPO%"\spring-tx-4.1.3.RELEASE.jar;"%REPO%"\reactor-core-1.1.4.RELEASE.jar;"%REPO%"\disruptor-3.2.1.jar;"%REPO%"\jsr166e-1.0.jar;"%REPO%"\spring-retry-1.1.1.RELEASE.jar;"%REPO%"\spring-aop-4.1.3.RELEASE.jar;"%REPO%"\aopalliance-1.0.jar;"%REPO%"\spring-context-4.1.3.RELEASE.jar;"%REPO%"\spring-expression-4.1.3.RELEASE.jar;"%REPO%"\junit-4.4.jar;"%REPO%"\kafka-clients-0.8.2.0.jar;"%REPO%"\lz4-1.2.0.jar;"%REPO%"\slf4j-api-1.7.6.jar;"%REPO%"\jcl-over-slf4j-1.7.6.jar;"%REPO%"\slf4j-log4j12-1.7.6.jar;"%REPO%"\log4j-1.2.17.jar;"%REPO%"\com.multilix.kafka-0.0.1-SNAPSHOT.jar
set EXTRA_JVM_ARGUMENTS=
goto endInit

@REM Reaching here means variables are defined and arguments have been captured
:endInit

%JAVACMD% %JAVA_OPTS% %EXTRA_JVM_ARGUMENTS% -classpath %CLASSPATH_PREFIX%;%CLASSPATH% -Dapp.name="transactionProducerApp" -Dapp.repo="%REPO%" -Dbasedir="%BASEDIR%" com.multilix.kafka.app.TransactionProducerApp %CMD_LINE_ARGS%
if ERRORLEVEL 1 goto error
goto end

:error
if "%OS%"=="Windows_NT" @endlocal
set ERROR_CODE=%ERRORLEVEL%

:end
@REM set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" goto endNT

@REM For old DOS remove the set variables from ENV - we assume they were not set
@REM before we started - at least we don't leave any baggage around
set CMD_LINE_ARGS=
goto postExec

:endNT
@REM If error code is set to 1 then the endlocal was done already in :error.
if %ERROR_CODE% EQU 0 @endlocal


:postExec

if "%FORCE_EXIT_ON_ERROR%" == "on" (
  if %ERROR_CODE% NEQ 0 exit %ERROR_CODE%
)

exit /B %ERROR_CODE%
