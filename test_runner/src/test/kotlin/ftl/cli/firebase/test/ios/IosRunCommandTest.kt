package ftl.cli.firebase.test.ios

import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import ftl.config.Device
import ftl.config.FtlConstants
import ftl.test.util.FlankTestRunner
import org.junit.Rule
import org.junit.Test
import org.junit.contrib.java.lang.system.ExpectedSystemExit
import org.junit.contrib.java.lang.system.SystemOutRule
import org.junit.runner.RunWith
import picocli.CommandLine

@RunWith(FlankTestRunner::class)
class IosRunCommandTest {
    @Rule
    @JvmField
    val systemOutRule: SystemOutRule = SystemOutRule().enableLog().muteForSuccessfulTests()

    @get:Rule
    val exit = ExpectedSystemExit.none()!!

    @Test
    fun iosRunCommandPrintsHelp() {
        val iosRun = IosRunCommand()
        assertThat(iosRun.usageHelpRequested).isFalse()
        CommandLine.run<Runnable>(iosRun, System.out, "-h")

        val output = systemOutRule.log
        Truth.assertThat(output).startsWith("Run tests on Firebase Test Lab")
        Truth.assertThat(output).contains("run [-h]")

        assertThat(iosRun.usageHelpRequested).isTrue()
    }

    @Test
    fun iosRunCommandRuns() {
        exit.expectSystemExit()
        val runCmd = IosRunCommand()
        runCmd.configPath = "./src/test/kotlin/ftl/fixtures/ios.yml"
        runCmd.run()

        val output = systemOutRule.log
        Truth.assertThat(output).contains("1 / 1 (100.00%)")
    }

    @Test
    fun iosRunCommandOptions() {
        val cmd = IosRunCommand()
        assertThat(cmd.configPath).isEqualTo(FtlConstants.defaultIosConfig)
        cmd.configPath = "tmp"
        assertThat(cmd.configPath).isEqualTo("tmp")

        assertThat(cmd.usageHelpRequested).isFalse()
        cmd.usageHelpRequested = true
        assertThat(cmd.usageHelpRequested).isTrue()
    }

    @Test
    fun empty_params_parse_null() {
        val cmd = IosRunCommand()
        CommandLine(cmd).parse()
        assertThat(cmd.resultsBucket).isNull()
        assertThat(cmd.recordVideo).isNull()
        assertThat(cmd.noRecordVideo).isNull()
        assertThat(cmd.timeout).isNull()
        assertThat(cmd.async).isNull()
        assertThat(cmd.project).isNull()
        assertThat(cmd.resultsHistoryName).isNull()
        assertThat(cmd.maxTestShards).isNull()
        assertThat(cmd.shardTime).isNull()
        assertThat(cmd.repeatTests).isNull()
        assertThat(cmd.testTargetsAlwaysRun).isNull()
        assertThat(cmd.testTargets).isNull()
        assertThat(cmd.filesToDownload).isNull()
        assertThat(cmd.disableSharding).isNull()
        assertThat(cmd.test).isNull()
        assertThat(cmd.xctestrunFile).isNull()
        assertThat(cmd.xcodeVersion).isNull()
        assertThat(cmd.device).isNull()
        assertThat(cmd.resultsDir).isNull()
        assertThat(cmd.flakyTestAttempts).isNull()
        assertThat(cmd.localResultsDir).isNull()
        assertThat(cmd.smartFlankDisableUpload).isNull()
    }

    @Test
    fun resultsBucket_parse() {
        val cmd = IosRunCommand()
        CommandLine(cmd).parse("--results-bucket=a")

        assertThat(cmd.resultsBucket).isEqualTo("a")
    }

    @Test
    fun recordVideo_parse() {
        val cmd = IosRunCommand()
        CommandLine(cmd).parse("--record-video")

        assertThat(cmd.recordVideo).isTrue()
    }

    @Test
    fun noRecordVideo_parse() {
        val cmd = IosRunCommand()
        CommandLine(cmd).parse("--no-record-video")

        assertThat(cmd.noRecordVideo).isTrue()
    }

    @Test
    fun timeout_parse() {
        val cmd = IosRunCommand()
        CommandLine(cmd).parse("--timeout=1m")

        assertThat(cmd.timeout).isEqualTo("1m")
    }

    @Test
    fun async_parse() {
        val cmd = IosRunCommand()
        CommandLine(cmd).parse("--async")

        assertThat(cmd.async).isTrue()
    }

    @Test
    fun project_parse() {
        val cmd = IosRunCommand()
        CommandLine(cmd).parse("--project=a")

        assertThat(cmd.project).isEqualTo("a")
    }

    @Test
    fun resultsHistoryName_parse() {
        val cmd = IosRunCommand()
        CommandLine(cmd).parse("--results-history-name=a")

        assertThat(cmd.resultsHistoryName).isEqualTo("a")
    }

    // flankYml

    @Test
    fun maxTestShards_parse() {
        val cmd = IosRunCommand()
        CommandLine(cmd).parse("--test-shards=3")

        assertThat(cmd.maxTestShards).isEqualTo(3)
    }

    @Test
    fun repeatTests_parse() {
        val cmd = IosRunCommand()
        CommandLine(cmd).parse("--repeat-tests=3")

        assertThat(cmd.repeatTests).isEqualTo(3)
    }

    @Test
    fun testTargetsAlwaysRun_parse() {
        val cmd = IosRunCommand()
        CommandLine(cmd).parse("--test-targets-always-run=a,b,c")

        assertThat(cmd.testTargetsAlwaysRun).isEqualTo(arrayListOf("a", "b", "c"))
    }

    @Test
    fun testTargets_parse() {
        val cmd = IosRunCommand()
        CommandLine(cmd).parse("--test-targets=a,b,c")

        assertThat(cmd.testTargets).isEqualTo(arrayListOf("a", "b", "c"))
    }

    @Test
    fun test_parse() {
        val cmd = IosRunCommand()
        CommandLine(cmd).parse("--test=a")

        assertThat(cmd.test).isEqualTo("a")
    }

    @Test
    fun xctestrunFile_parse() {
        val cmd = IosRunCommand()
        CommandLine(cmd).parse("--xctestrun-file=a")

        assertThat(cmd.xctestrunFile).isEqualTo("a")
    }

    @Test
    fun xcodeVersion_parse() {
        val cmd = IosRunCommand()
        CommandLine(cmd).parse("--xcode-version=999")

        assertThat(cmd.xcodeVersion).isEqualTo("999")
    }

    @Test
    fun device_parse() {
        val cmd = IosRunCommand()
        CommandLine(cmd).parse("--device=model=iphone8,version=11.2,locale=zh_CN,orientation=default")

        val expectedDevice = Device("iphone8", "11.2", "zh_CN", "default")
        assertThat(cmd.device?.size).isEqualTo(1)
        assertThat(cmd.device?.first()).isEqualTo(expectedDevice)
    }

    @Test
    fun resultsDir_parse() {
        val cmd = IosRunCommand()
        CommandLine(cmd).parse("--results-dir=a")

        assertThat(cmd.resultsDir).isEqualTo("a")
    }

    @Test
    fun filesToDownload_parse() {
        val cmd = IosRunCommand()
        CommandLine(cmd).parse("--files-to-download=a,b")

        assertThat(cmd.filesToDownload).isEqualTo(arrayListOf("a", "b"))
    }

    @Test
    fun flakyTestAttempts_parse() {
        val cmd = IosRunCommand()
        CommandLine(cmd).parse("--flaky-test-attempts=10")

        assertThat(cmd.flakyTestAttempts).isEqualTo(10)
    }

    @Test
    fun `shardTime parse`() {
        val cmd = IosRunCommand()
        CommandLine(cmd).parse("--shard-time=99")

        assertThat(cmd.shardTime).isEqualTo(99)
    }

    @Test
    fun `disableShard parse`() {
        val cmd = IosRunCommand()
        CommandLine(cmd).parse("--disable-sharding")

        assertThat(cmd.disableSharding).isEqualTo(true)
    }

    @Test
    fun `local-results-dir parse`() {
        val cmd = IosRunCommand()
        CommandLine(cmd).parse("--local-result-dir=a")

        assertThat(cmd.localResultsDir).isEqualTo("a")
    }

    @Test
    fun `smart-flank-disable-upload parse`() {
        val cmd = IosRunCommand()
        CommandLine(cmd).parse("--smart-flank-disable-upload=true")

        assertThat(cmd.smartFlankDisableUpload).isEqualTo(true)
    }
}
