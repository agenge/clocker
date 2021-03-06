/*
 * Copyright 2014-2015 by Cloudsoft Corporation Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package brooklyn.networking.sdn;

import java.net.InetAddress;

import brooklyn.entity.annotation.Effector;
import brooklyn.entity.annotation.EffectorParam;
import brooklyn.entity.basic.ConfigKeys;
import brooklyn.entity.basic.MethodEffector;
import brooklyn.entity.basic.SoftwareProcess;
import brooklyn.entity.container.docker.DockerHost;
import brooklyn.event.AttributeSensor;
import brooklyn.event.basic.AttributeSensorAndConfigKey;
import brooklyn.event.basic.Sensors;
import brooklyn.networking.VirtualNetwork;
import brooklyn.networking.subnet.SubnetTier;
import brooklyn.util.flags.SetFromFlag;

/**
 * An SDN agent process.
 */
public interface SdnAgent extends SoftwareProcess {


    @SetFromFlag("host")
    AttributeSensorAndConfigKey<DockerHost,DockerHost> DOCKER_HOST = ConfigKeys.newSensorAndConfigKey(DockerHost.class, "sdn.agent.docker.host", "Docker host we are running on");

    @SetFromFlag("provider")
    AttributeSensorAndConfigKey<SdnProvider,SdnProvider> SDN_PROVIDER = ConfigKeys.newSensorAndConfigKey(SdnProvider.class, "sdn.provider", "SDN provider entity");
 
    AttributeSensor<InetAddress> SDN_AGENT_ADDRESS = Sensors.newSensor(InetAddress.class, "sdn.agent.address", "IP address of SDN agent service");
    AttributeSensor<SdnAgent> SDN_AGENT = Sensors.newSensor(SdnAgent.class, "sdn.agent.entity", "SDN agent entity");

    DockerHost getDockerHost();

    String provisionNetwork(VirtualNetwork network);

    MethodEffector<InetAddress> ATTACH_NETWORK = new MethodEffector<InetAddress>(SdnAgent.class, "attachNetwork");

    /**
     * Attach a container to a network.
     *
     * @param containerId the container ID
     * @param networkId the network ID to attach
     * @return the {@link SubnetTier} IP address
     */
    @Effector(description="Attach a container to a network")
    InetAddress attachNetwork(
            @EffectorParam(name="containerId", description="Container ID") String containerId,
            @EffectorParam(name="networkId", description="Network ID") String networkId);

}
