package ru.otus.otuskotlin.marketplace.common.exceptions

import models.MkplCommand

class UnknownMkplCommand(command: MkplCommand) : Throwable("Wrong command $command at mapping toTransport stage")
