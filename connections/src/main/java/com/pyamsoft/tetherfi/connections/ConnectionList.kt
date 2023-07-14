package com.pyamsoft.tetherfi.connections

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.pyamsoft.pydroid.theme.keylines
import com.pyamsoft.tetherfi.server.clients.TetherClient
import com.pyamsoft.tetherfi.server.clients.key
import com.pyamsoft.tetherfi.server.widi.WiDiNetworkStatus

fun LazyListScope.renderList(
    group: WiDiNetworkStatus.GroupInfo,
    clients: SnapshotStateList<TetherClient>,
    blocked: SnapshotStateList<TetherClient>,
    onToggleBlock: (TetherClient) -> Unit,
) {
  group.also { gi ->
    if (gi is WiDiNetworkStatus.GroupInfo.Connected) {
      if (clients.isEmpty()) {
        renderRunningNoClients()
      } else {
        renderRunningWithClients(
            clients = clients,
            blocked = blocked,
            onToggleBlock = onToggleBlock,
        )
      }
    } else {
      renderNotRunning()
    }
  }
}

private fun LazyListScope.renderRunningNoClients() {
  item(
      contentType = ConnectionScreenContentTypes.EMPTY,
  ) {
    Text(
        modifier =
            Modifier.fillMaxWidth()
                .padding(vertical = MaterialTheme.keylines.content)
                .padding(top = MaterialTheme.keylines.content * 3),
        text = "No connections yet!",
        style = MaterialTheme.typography.h5,
        textAlign = TextAlign.Center,
    )
  }
}

private fun LazyListScope.renderRunningWithClients(
    clients: SnapshotStateList<TetherClient>,
    blocked: SnapshotStateList<TetherClient>,
    onToggleBlock: (TetherClient) -> Unit,
) {
  item(
      contentType = ConnectionScreenContentTypes.HEADER,
  ) {
    Text(
        modifier =
            Modifier.fillMaxWidth()
                .padding(vertical = MaterialTheme.keylines.content)
                .padding(top = MaterialTheme.keylines.content * 3),
        text =
            "By default, any connecting client is allowed to access the Internet through the Hotspot. If you want to block a client from the network, you can toggle the switch off for the IP address you wish to restrict.",
        style =
            MaterialTheme.typography.body2.copy(
                color =
                    MaterialTheme.colors.onBackground.copy(
                        alpha = ContentAlpha.medium,
                    ),
            ),
        textAlign = TextAlign.Center,
    )
  }

  items(
      items = clients,
      key = { it.key() },
      contentType = { ConnectionScreenContentTypes.CLIENT },
  ) { client ->
    ConnectionItem(
        modifier = Modifier.fillMaxWidth(),
        client = client,
        blocked = blocked,
        onClick = onToggleBlock,
    )
  }
}

private fun LazyListScope.renderNotRunning() {
  item(
      contentType = ConnectionScreenContentTypes.START,
  ) {
    Text(
        modifier =
            Modifier.fillMaxWidth()
                .padding(vertical = MaterialTheme.keylines.content)
                .padding(top = MaterialTheme.keylines.content * 3),
        text = "Start the Hotspot to view and manage connected devices.",
        style = MaterialTheme.typography.h5,
        textAlign = TextAlign.Center,
    )
  }
}
