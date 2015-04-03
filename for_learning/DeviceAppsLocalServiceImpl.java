package cn.uc.mfw.service.device.persistence.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.uc.mfw.dao.DeviceAppsLocalMapper;
import cn.uc.mfw.entity.DeviceApps;
import cn.uc.mfw.entity.DeviceAppsLocal;
import cn.uc.mfw.service.device.persistence.DeviceAppsLocalService;

/**
 * <b><code>DeviceAppsLocalDao</code></b>
 * <p>
 * 
 * </p>
 * <p>@author huangquan@ucweb.com</p>
 * Creation Time: 2014年9月20日 下午2:16:46
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class DeviceAppsLocalServiceImpl implements DeviceAppsLocalService {

	@Resource
	private DeviceAppsLocalMapper deviceAppsLocalMapper;

	public int insert(DeviceAppsLocal app) {
		return deviceAppsLocalMapper.insert(app);
	}

	/**
	 * 分批次批量插入记录(每次插入100个应用数据, 以防止一次性插入时, 组装SQL过长.)
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int insertMultiple(List<DeviceAppsLocal> localApp) {
		if (null == localApp || localApp.size() == 0) {
			return 0;
		}

		int rows = 0;
		int fromIndex = 0;
		int toIndex = 0;
		for (;;) {
			int remainSize = localApp.size() - toIndex;
			toIndex += (remainSize > 100 ? 100 : remainSize);
			List<DeviceAppsLocal> subList = localApp.subList(fromIndex, toIndex);
			rows += deviceAppsLocalMapper.insertMultiple(subList);
			fromIndex = toIndex;
			if (toIndex >= localApp.size()) {
				break;
			}
		}
		return rows;
	}

	public List<DeviceApps> findLocalUrls(String deviceSn) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("deviceSn", deviceSn);
		return deviceAppsLocalMapper.findLocalUrls(params);
	}

	public DeviceAppsLocal findBy(String deviceSn, Integer appId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("deviceSn", deviceSn);
		params.put("appId", appId);
		List<DeviceAppsLocal> result = deviceAppsLocalMapper.findBy(params);
		if (result == null || result.isEmpty()) {
			return null;
		}
		return result.get(0);
	}

	public List<DeviceAppsLocal> findBy(String deviceSn) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("deviceSn", deviceSn);
		return deviceAppsLocalMapper.findBy(params);
	}

	/**
	 * 修改没有变化的应用所属的列表版本号
	 * @param changeAppIds
	 * @param listVersion
	 * @throws Exception 
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateListVersion(Collection<Integer> appIds, Integer listVersion, String deviceSn) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("listVersion", listVersion);
		params.put("deviceSn", deviceSn);
		params.put("appIds", appIds);
		params.put("modifyTime", new Date());
		return deviceAppsLocalMapper.updateListVersion(params);
	}

	/**
	 * 删除路由器某个本地应用
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int delete(String deviceSn, Integer appId) {
		List<Integer> appIds = null;
		if (appId != null) {
			appIds = new ArrayList<Integer>();
			appIds.add(appId);
		}
		return deleteMultiple(deviceSn, appIds);
	}

	/**
	 * @see cn.uc.mfw.service.device.persistence.DeviceAppsLocalService#deleteMultiple(java.lang.String, java.util.List)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int deleteMultiple(String deviceSn, Collection<Integer> appIds) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("deviceSn", deviceSn);
		params.put("appIds", appIds);
		return deviceAppsLocalMapper.deleteMultiple(params);
	}

	/**
	 * 删除路由器所有本地应用
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int delete(String deviceSn) {
		return deleteMultiple(deviceSn, null);
	}

	/**
	 * @see cn.uc.mfw.service.device.persistence.DeviceAppsLocalService#update(cn.uc.mfw.entity.DeviceAppsLocal)
	 */
	@Override
	public int update(DeviceAppsLocal localApp) {
		return deviceAppsLocalMapper.updateByPrimaryKeySelective(localApp);
	}
}
