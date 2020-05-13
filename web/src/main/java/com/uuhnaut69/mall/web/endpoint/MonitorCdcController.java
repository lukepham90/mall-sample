package com.uuhnaut69.mall.web.endpoint;

import com.uuhnaut69.mall.cdc.listener.CDCListener;
import com.uuhnaut69.mall.core.constant.MessageConstant;
import com.uuhnaut69.mall.core.constant.UrlConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.Timestamp;

/**
 * @author uuhnaut
 * @project mall
 * @date 5/13/20
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = UrlConstants.BASE_VERSION_API + UrlConstants.ADMIN_URL + UrlConstants.FILE_URL)
@Api(tags = "Monitor", value = "Monitor Data Capture Change Endpoint")
public class MonitorCdcController {

    private final CDCListener cdcListener;

    @ApiOperation(value = "Start Monitor Endpoint", notes = "Admin endpoint")
    @PostMapping("/start")
    public String start(@RequestParam(value = "fromCheckpointTime", required = false) Timestamp fromCheckpointTime) {
        cdcListener.start(fromCheckpointTime);
        return MessageConstant.START_SUCCESSFULLY;
    }

    @ApiOperation(value = "Stop Monitor Endpoint", notes = "Admin endpoint")
    @PostMapping("/stop")
    public String stop() throws IOException {
        cdcListener.stop();
        return MessageConstant.STOP_SUCCESSFULLY;
    }
}
