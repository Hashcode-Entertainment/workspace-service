import com.workspaceservice.Workspace
import com.workspaceservice.WorkspaceManager
import com.workspaceservice.git.GitServer
import com.workspaceservice.user.User
import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.net.URL
import java.util.*

class WorkspaceManagerTest : WordSpec({
    "createWorkspace()" should {
        "create a git repo" {
            val gitServer = mockk<GitServer>(relaxed = true)
            val workspaceManager = WorkspaceManager(gitServer, URL("https://workspaces.test.com"))
            val owner = mockk<User>()
            workspaceManager.createWorkspace(owner, null)
            verify {
                gitServer.createRepo(any())
            }
        }

        "create a workspace with a valid url" {
            val gitServer = mockk<GitServer>(relaxed = true)
            every { gitServer.createRepo(any()) } returns "/2e4d6c9e-e0d7-4cd4-a931-324e37f8dc39.git"
            val workspaceManager = WorkspaceManager(gitServer, URL("https://workspaces.test.com"))
            val owner = mockk<User>()
            val workspace = workspaceManager.createWorkspace(owner, null)
            workspace.url shouldBe URL("https://workspaces.test.com/2e4d6c9e-e0d7-4cd4-a931-324e37f8dc39.git")
        }

        "create a workspace with a valid templateId" {
            val gitServer = mockk<GitServer>(relaxed = true)
            val workspaceManager = WorkspaceManager(gitServer, URL("https://workspaces.test.com"))
            val owner = mockk<User>()
            val template = mockk<Workspace>(relaxed = true)
            every { template.id } returns UUID.fromString("8d2debc6-3880-4a33-93bd-ece541c6d27f")
            val workspace = workspaceManager.createWorkspace(owner, template)
            workspace.template shouldBe UUID.fromString("8d2debc6-3880-4a33-93bd-ece541c6d27f")
        }
    }

    "deleteRepo()" should {
        "delete a git repo" {
            val gitServer = mockk<GitServer>(relaxed = true)
            val workspaceManager = WorkspaceManager(gitServer, URL("https://workspaces.test.com"))
            workspaceManager.deleteWorkspace(UUID.fromString("8d2debc6-3880-4a33-93bd-ece541c6d27f"))
            verify {
                gitServer.deleteRepo("8d2debc6-3880-4a33-93bd-ece541c6d27f")
            }
        }
    }
})