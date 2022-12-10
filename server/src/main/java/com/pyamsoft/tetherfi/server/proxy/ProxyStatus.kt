package com.pyamsoft.tetherfi.server.proxy

import com.pyamsoft.tetherfi.server.ServerInternalApi
import com.pyamsoft.tetherfi.server.status.BaseStatusBroadcaster
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@ServerInternalApi
internal class ProxyStatus @Inject internal constructor() : BaseStatusBroadcaster()
