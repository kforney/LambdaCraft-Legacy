/** 
 * Copyright (c) LambdaCraft Modding Team, 2013
 * 版权许可：LambdaCraft 制作小组， 2013.
 * http://lambdacraft.half-life.cn/
 * 
 * LambdaCraft is open-source. It is distributed under the terms of the
 * LambdaCraft Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 *
 * LambdaCraft是完全开源的。它的发布遵从《LambdaCraft开源协议》。你允许阅读，修改以及调试运行
 * 源代码， 然而你不允许将源代码以另外任何的方式发布，除非你得到了版权所有者的许可。
 */
package cn.lambdacraft.deathmatch.proxy;

import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import cn.lambdacraft.core.prop.ClientProps;
import cn.lambdacraft.crafting.register.CBCItems;
import cn.lambdacraft.deathmatch.block.TileArmorCharger;
import cn.lambdacraft.deathmatch.block.TileHealthCharger;
import cn.lambdacraft.deathmatch.block.TileTripmine;
import cn.lambdacraft.deathmatch.client.DMClientEventHandler;
import cn.lambdacraft.deathmatch.client.model.Model357;
import cn.lambdacraft.deathmatch.client.model.ModelAR;
import cn.lambdacraft.deathmatch.client.model.ModelBattery;
import cn.lambdacraft.deathmatch.client.model.ModelGrenade;
import cn.lambdacraft.deathmatch.client.model.ModelHandgun;
import cn.lambdacraft.deathmatch.client.model.ModelMedkit;
import cn.lambdacraft.deathmatch.client.model.ModelRocket;
import cn.lambdacraft.deathmatch.client.model.ModelUranium;
import cn.lambdacraft.deathmatch.client.renderer.*;
import cn.lambdacraft.deathmatch.entity.EntityARGrenade;
import cn.lambdacraft.deathmatch.entity.EntityBattery;
import cn.lambdacraft.deathmatch.entity.EntityBulletGauss;
import cn.lambdacraft.deathmatch.entity.EntityBulletGaussSec;
import cn.lambdacraft.deathmatch.entity.EntityCrossbowArrow;
import cn.lambdacraft.deathmatch.entity.EntityHGrenade;
import cn.lambdacraft.deathmatch.entity.EntityHornet;
import cn.lambdacraft.deathmatch.entity.EntityMedkit;
import cn.lambdacraft.deathmatch.entity.EntityRPGDot;
import cn.lambdacraft.deathmatch.entity.EntityRocket;
import cn.lambdacraft.deathmatch.entity.EntitySatchel;
import cn.lambdacraft.deathmatch.entity.fx.EntityCrossbowStill;
import cn.lambdacraft.deathmatch.entity.fx.EntityEgonRay;
import cn.lambdacraft.deathmatch.entity.fx.EntityGaussRay;
import cn.lambdacraft.deathmatch.entity.fx.EntityGaussRayColored;
import cn.lambdacraft.deathmatch.entity.fx.GaussParticleFX;
import cn.lambdacraft.deathmatch.flashlight.ClientTickHandler;
import cn.lambdacraft.deathmatch.register.DMBlocks;
import cn.lambdacraft.deathmatch.register.DMItems;
import cn.liutils.api.client.LIClientRegistry;
import cn.liutils.api.client.render.RenderCrossedProjectile;
import cn.liutils.api.client.render.RenderEmpty;
import cn.liutils.api.client.render.RenderIcon;
import cn.liutils.api.client.render.RenderModel;
import cn.liutils.api.client.render.RenderModelItem;
import cn.liutils.api.client.render.RenderModelProjectile;
import cn.weaponmod.api.client.render.RendererBulletWeapon;
import cn.weaponmod.api.client.render.RendererModelBulletWeapon;
import cn.weaponmod.api.weapon.WeaponGeneric;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

/**
 * @author WeAthFolD
 * 
 */
public class ClientProxy extends Proxy {

    public static ClientTickHandler cth;

    
    @Override
    public void preInit() {
        MinecraftForge.EVENT_BUS.register(new DMClientEventHandler());
    }
    
    @Override
    public void init() {

        RenderingRegistry.registerEntityRenderingHandler(EntityHGrenade.class, new RenderSnowball(DMItems.weapon_hgrenade));
        RenderingRegistry.registerEntityRenderingHandler(EntityGaussRay.class, new RenderGaussRay(false));
        RenderingRegistry.registerEntityRenderingHandler(EntityGaussRayColored.class, new RenderGaussRay(true));
        RenderingRegistry.registerEntityRenderingHandler(EntitySatchel.class,new RenderSatchel());
        RenderingRegistry.registerEntityRenderingHandler(EntityARGrenade.class,new RenderModelProjectile(new ModelGrenade(), ClientProps.AR_GRENADE_PATH));
        RenderingRegistry.registerEntityRenderingHandler(EntityEgonRay.class,    new RenderEgonRay());
        RenderingRegistry.registerEntityRenderingHandler(EntityRocket.class,new RenderModelProjectile(new ModelRocket(), ClientProps.RPG_ROCKET_PATH));
        RenderingRegistry.registerEntityRenderingHandler(EntityCrossbowArrow.class, new RenderCrossedProjectile(0.6,0.12, ClientProps.CROSSBOW_BOW_PATH));
        RenderingRegistry.registerEntityRenderingHandler(EntityCrossbowStill.class, new RenderCrossedProjectile(0.6,0.12, ClientProps.CROSSBOW_BOW_PATH));
        RenderingRegistry.registerEntityRenderingHandler(EntityRPGDot.class,new RenderIcon(ClientProps.RED_DOT_PATH).setBlend(0.8F).setHasLight(false));
        RenderingRegistry.registerEntityRenderingHandler(EntityBulletGauss.class, new RenderEmpty());
        RenderingRegistry.registerEntityRenderingHandler(EntityBulletGaussSec.class, new RenderEmpty());
        RenderingRegistry.registerEntityRenderingHandler(EntityHornet.class, new RenderHornet());
        RenderingRegistry.registerEntityRenderingHandler(EntityBattery.class,new RenderModel(new ModelBattery(), ClientProps.BATTERY_PATH,0.5F));
        RenderingRegistry.registerEntityRenderingHandler(EntityMedkit.class,new RenderModel(new ModelMedkit(), ClientProps.MEDKIT_ENT_PATH,1.0F));
        RenderingRegistry.registerEntityRenderingHandler(GaussParticleFX.class, new RenderGlow());
        RendererModelBulletWeapon handgun_render = new RendererModelBulletWeapon(new ModelHandgun(), (WeaponGeneric) DMItems.weapon_9mmhandgun, ClientProps.HANDGUN_MDL_PATH);
        RendererModelBulletWeapon pyt_render = new RendererModelBulletWeapon(new Model357(), (WeaponGeneric) DMItems.weapon_357, ClientProps.PYTHON_MDL_PATH);
        RendererModelBulletWeapon ar_render = new RendererModelBulletWeapon(new ModelAR(), (WeaponGeneric) DMItems.weapon_9mmAR, ClientProps.NMMAR_MDL_PATH);
        RenderModelItem uranium_render = new RenderModelItem(new ModelUranium(), ClientProps.URANIUM_MDL_PATH);
        
        pyt_render.setStdRotation(0F, 180F, 0F)
            .setOffset(0.002F, 0.402F, -0.314F)
            .setScale(1.212F)
            .setStdRotation(0F, 181.13414F, 0F)
            .setEquipOffset(1.15, -0.447, 0.154)
            .setEquipRotation(0.066F, 0.844F, -8.483F)
            .setInventorySpin(false)
            .setInvOffset(-2.45F, 3.04F)
            .setInvScale(0.912F)
            .setInvRotation(-35.796F, -94.77F, -3.452F);
        handgun_render.setInventorySpin(false)
            .setStdRotation(0F, 180F, 0F)
            .setOffset(0.0F, 0.11F, -0.30F)
            .setEquipOffset(0.91F, -0.12F, 0.132F)
            .setScale(2.24F)
            .setInvOffset(-0.422F, 1.90F)
            .setInvScale(1.164F)
            .setInvRotation(-40.54F, -66F, 8F);
        ar_render.setOffset(0.0F, 0.242F, -0.588F)
            .setEquipOffset(0.852F, -0.118F, -0.01F)
            .setScale(1.352F)
            .setStdRotation(0F, -177.768F, 0F)
            .setInventorySpin(false)
            .setInvOffset(-0.408F, 2.75F)
            .setInvScale(0.908F)
            .setInvRotation(-42.78F, -65.428F, -11F)
            .setUpliftFactor(0.5F)
            .setRecoilVec(-0.05f, .008f, 0);

        uranium_render.setInventorySpin(false)
            .setOffset(0F, -0.14F, 0F)
            .setEquipRotation(-130.276F, -42.034F, -101.67F)
            .setEquipOffset(0.562F, 0.118F, -0.248F)
            .setStdRotation(0F, 0F, 0F)
            .setInvRotation(0F, -45F, -26F)
            .setInvOffset(0.01F, 2.318F)
            .setScale(1.05F)
            .setEntityItemRotation(0F, 0F, 0F)
            .setInvScale(1.424F);
        MinecraftForgeClient.registerItemRenderer(DMItems.crossbow, new RenderCrossbow());
        MinecraftForgeClient.registerItemRenderer(DMItems.weapon_egon,new RenderEgon());
        MinecraftForgeClient.registerItemRenderer(DMItems.gauss,new RenderGauss());
        MinecraftForgeClient.registerItemRenderer(DMItems.weapon_9mmhandgun, handgun_render);
        MinecraftForgeClient.registerItemRenderer(DMItems.weapon_357, pyt_render);
        MinecraftForgeClient.registerItemRenderer(DMItems.weapon_shotgun,new RendererBulletWeapon((WeaponGeneric) DMItems.weapon_shotgun,0.08F));
        MinecraftForgeClient.registerItemRenderer(DMItems.weapon_9mmAR, ar_render);
        MinecraftForgeClient.registerItemRenderer(DMItems.rpg, new RendererBulletWeapon(DMItems.rpg, 0.15F));
        MinecraftForgeClient.registerItemRenderer(DMItems.weapon_crowbar_el, new RenderItemElCrowbar());
        MinecraftForgeClient.registerItemRenderer(CBCItems.ammo_uranium, uranium_render);
        LIClientRegistry.addPlayerRenderingHelper(new RenderHelperEgon());
        ClientRegistry.bindTileEntitySpecialRenderer(TileTripmine.class,new RenderTileTripmine(DMBlocks.blockTripmine));
        ClientRegistry.bindTileEntitySpecialRenderer(TileArmorCharger.class,new RenderTileCharger(DMBlocks.armorCharger));
        ClientRegistry.bindTileEntitySpecialRenderer(TileHealthCharger.class,new RenderTileHeCharger(DMBlocks.healthCharger));
        cth = new ClientTickHandler();
    }
}
