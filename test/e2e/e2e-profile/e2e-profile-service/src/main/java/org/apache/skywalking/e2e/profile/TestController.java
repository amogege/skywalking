/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.apache.skywalking.e2e.profile;

import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * @author MrPro
 */
@RestController
@RequestMapping("/e2e")
public class TestController {
    private final UserRepo userRepo;

    public TestController(final UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/health-check")
    public String hello() {
        return "healthy";
    }

    @PostMapping("/users")
    public User createAuthor(@RequestBody final CreateUser createUser) throws InterruptedException {
        final User user = userRepo.save(createUser.toUser());
        if (!createUser.getEnableProfiling()) {
            return user;
        } else {
            // sleep 6200 milliseconds
            TimeUnit.MILLISECONDS.sleep(6200);
            return user;
        }
    }
}
